package serviceserver;

import Service.PurchasedService;
import domain.Purchased;
import domain.validators.ValidatorException;
import repo.SortingRepository;

public class PurchasedServiceServer extends ServiceServer<Long, Purchased> implements PurchasedService {
    private BookServiceServer bookServiceServer;
    private ClientServiceServer clientServiceServer;

    public PurchasedServiceServer(SortingRepository<Long, Purchased> _repo,  BookServiceServer _bookServiceServer, ClientServiceServer _clientServiceServer) {
        super(_repo);
        bookServiceServer=_bookServiceServer;
        clientServiceServer=_clientServiceServer;
    }

    @Override
    public boolean add(Purchased entity) throws ValidatorException {
        Long bookID=entity.getBookID();
        Long clientID=entity.getClientID();

        bookServiceServer.getOne(bookID);
        clientServiceServer.getOne(clientID);

        repository.save(entity);
        return true;
    }

}
