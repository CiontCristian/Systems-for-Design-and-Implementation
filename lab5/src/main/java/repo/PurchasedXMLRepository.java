package repo;

import domain.Client;
import domain.Purchased;
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

public class PurchasedXMLRepository extends InMemoryRepository<Long, Purchased>{
    private String fileName;

    public PurchasedXMLRepository(Validator<Purchased> validator,String fileName) {
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
        }catch(ParserConfigurationException| IOException | SAXException e){
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
            Node purchasedNode = children.item(index);
            if (purchasedNode instanceof Element) {
                Purchased newPurchased = createPurchasedFromElement((Element) purchasedNode);
                super.save(newPurchased);
            }
        }


    }
    private static Purchased createPurchasedFromElement(Element clientElement) {
        Purchased purchased = new Purchased();
        long id = Long.parseLong(clientElement.getAttribute("id"));
        purchased.setId(id);
        purchased.setBookID(Long.parseLong(getTextFromTagName(clientElement, "bookID")));
        purchased.setClientID(Long.parseLong(getTextFromTagName(clientElement, "clientID")));
        //purchased.setAge(Integer.parseInt(getTextFromTagName(clientElement, "age")));
        return purchased;
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
    public void savePurchased(Purchased purchased) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(fileName);
        Element root = document.getDocumentElement();
        Node purchasedNode = purchasedToNode(purchased, document);
        root.appendChild(purchasedNode);
        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(fileName)));

    }
    public static Node purchasedToNode(Purchased purchased, Document document) {
        Element purchasedElement = document.createElement("purchased");
        purchasedElement.setAttribute("id", purchased.getId()+"");
        appendChildWithTextToNode(document, purchasedElement, "bookID", purchased.getBookID()+"");
        appendChildWithTextToNode(document, purchasedElement, "clientID", purchased.getClientID()+"");
        return purchasedElement;
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
        Iterable<Purchased> allPurchased=super.findAll();
        createDoc();
        for(Purchased purchased : allPurchased){
            savePurchased(purchased);
        }

    }
    @Override
    public Optional<Purchased> save(Purchased entity) throws ValidatorException {
        Optional<Purchased> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }

        try {
            savePurchased(entity);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
    @Override
    public Optional<Purchased> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Purchased> optional=super.delete(id);

        try {
            saveAllToFile();
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return optional;
    }
    @Override
    public Optional<Purchased> update(Purchased entity) throws ValidatorException {
        Optional<Purchased> optional = super.update(entity);

        try {
            saveAllToFile();
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return optional;
    }


}
