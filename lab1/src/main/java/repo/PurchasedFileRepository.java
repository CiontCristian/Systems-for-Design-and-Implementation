package repo;


import domain.Purchased;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PurchasedFileRepository extends InMemoryRepository<Long, Purchased> {
    private String fileName;

    public PurchasedFileRepository(Validator<Purchased> validator, String _fileName){
        super(validator);
        fileName=_fileName;
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                long bookID = Long.parseLong(items.get(1));
                long clientID = Long.parseLong(items.get(2));

                Purchased purchased=new Purchased(bookID, clientID);
                purchased.setId(id);;

                try {
                    super.save(purchased);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveToFile(Purchased entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getBookID() + "," + entity.getClientID());
            bufferedWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAllToFile(){
        Iterable<Purchased> allPurchased=super.findAll();

        allPurchased.forEach(this::saveToFile);
    }

    @Override
    public Optional<Purchased> save(Purchased entity) throws ValidatorException {
        Optional<Purchased> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Purchased> delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Purchased> optional=super.delete(id);

        saveAllToFile();
        return optional;
    }

    @Override
    public Optional<Purchased> update(Purchased entity) throws ValidatorException {
        Optional<Purchased> optional = super.update(entity);

        saveAllToFile();

        return optional;
    }
}
