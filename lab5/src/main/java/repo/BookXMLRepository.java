package repo;

import domain.Book;
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


public class BookXMLRepository extends InMemoryRepository<Long, Book>{
    private String fileName;

    public BookXMLRepository(Validator<Book> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        final File newXmlFile = new File(
                 fileName);
        if(!newXmlFile.exists())
        try {
            createDoc();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        try {
            loadData();
        } catch (ParserConfigurationException | IOException | SAXException e) {
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
            Node bookNode = children.item(index);
            if (bookNode instanceof Element) {
                Book newBook = createBookFromElement((Element) bookNode);
                super.save(newBook);
            }
        }


    }
    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }
    private static Book createBookFromElement(Element bookElement) {
        Book book = new Book();
        long id = Long.parseLong(bookElement.getAttribute("id"));
        book.setId(id);
        book.setTitle(getTextFromTagName(bookElement, "title"));
        book.setAuthor(getTextFromTagName(bookElement, "author"));
        book.setPrice(Float.parseFloat(getTextFromTagName(bookElement, "price")));
        return book;
    }

    public void saveBook(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {

            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);
            Element root = document.getDocumentElement();
                Node bookNode = bookToNode(book, document);
                root.appendChild(bookNode);
            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(new File(fileName)));

    }
    public static Node bookToNode(Book book, Document document) {
        Element bookElement = document.createElement("book");
        bookElement.setAttribute("id", book.getId()+"");
        appendChildWithTextToNode(document, bookElement, "title", book.getTitle());
        appendChildWithTextToNode(document, bookElement, "author", book.getAuthor());
        appendChildWithTextToNode(document, bookElement, "price", String.valueOf(book.getPrice()));
        return bookElement;
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
        Iterable<Book> allBooks=super.findAll();
        createDoc();
        for(Book book : allBooks){
            saveBook(book);
        }

    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        Optional<Book> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }

        try {
            saveBook(entity);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Book> optional=super.delete(id);

        try {
            saveAllToFile();
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
        return optional;
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        Optional<Book> optional = super.update(entity);

        try {
            saveAllToFile();
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return optional;
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
}
