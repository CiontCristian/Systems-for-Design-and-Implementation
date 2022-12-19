package repo;

import domain.Client;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ClientXMLRepository extends InMemoryRepository<Long, Client>{
    private String fileName;
    public ClientXMLRepository(Validator<Client> validator,String fileName){
        super(validator);
        this.fileName=fileName;
        final File newXmlFile= new File(fileName);
        if(!newXmlFile.exists())
            try{
                createDoc();
            } catch(ParserConfigurationException | TransformerException e){
                e.printStackTrace();
            }
        try{
            loadData();
        }catch(ParserConfigurationException| IOException| SAXException e){
            e.printStackTrace();
        }
    }
    private void loadData() throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(fileName);

        Element root = document.getDocumentElement();
        NodeList children = root.getChildNodes();
        /*
        IntStream.range(0, children.getLength())
                .mapToObj(children::item)
                .filter(node -> node instanceof Element)
                .map(node -> createBookFromElement((Element) node))
                .map(super::save);
                //.collect(Collectors.toMap(Book::getId,Book));
        */
        for (int index = 0; index < children.getLength(); index++){
            Node clientNode = children.item(index);
            if (clientNode instanceof Element) {
                Client newClient = createClientFromElement((Element) clientNode);
                super.save(newClient);
            }
        }


    }
    private static Client createClientFromElement(Element clientElement) {
        Client client = new Client();
        long id = Long.parseLong(clientElement.getAttribute("id"));
        client.setId(id);
        client.setFirstName(getTextFromTagName(clientElement, "firstName"));
        client.setLastName(getTextFromTagName(clientElement, "lastName"));
        client.setAge(Integer.parseInt(getTextFromTagName(clientElement, "age")));
        return client;
    }
    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }
    private void createDoc() throws ParserConfigurationException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();
        Element root = document.createElement("root");
        document.appendChild(root);
        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(fileName)));
    }
    public void saveClient(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(fileName);
        Element root = document.getDocumentElement();
        Node clientNode = clientToNode(client, document);
        root.appendChild(clientNode);
        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(fileName)));

    }
    public static Node clientToNode(Client client, Document document) {
        Element clientElement = document.createElement("client");
        clientElement.setAttribute("id", client.getId()+"");
        appendChildWithTextToNode(document, clientElement, "firstName", client.getFirstName());
        appendChildWithTextToNode(document, clientElement, "lastName", client.getLastName());
        appendChildWithTextToNode(document, clientElement, "age", String.valueOf(client.getAge()));
        return clientElement;
    }

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    private void saveAllToFile() throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Iterable<Client> allClients=super.findAll();
        createDoc();
        for(Client client : allClients){
            saveClient(client);
        }

    }
    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }

        try {
            saveClient(entity);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Client> optional=super.delete(id);

        try {
            saveAllToFile();
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return optional;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        Optional<Client> optional = super.update(entity);

        try {
            saveAllToFile();
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return optional;
    }

}
