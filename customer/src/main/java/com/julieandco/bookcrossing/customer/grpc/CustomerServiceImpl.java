package com.julieandco.bookcrossing.customer.grpc;
import com.julieandco.bookcrossing.customer.entity.Customer;
import com.julieandco.bookcrossing.customer.service.CustomerService;
import com.julieandco.bookcrossing.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GrpcService
@Service
public class CustomerServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private com.julieandco.bookcrossing.customer.service.CustomerService customerService;
    @Autowired
    public CustomerServiceImpl(CustomerService customerService){
        this.customerService=customerService;
    }
    @Override
    public void customerAdd(
            UserRequestToAdd request, StreamObserver<UserResponseAdded> responseObserver) {

        String info = new StringBuilder()
                .append("User ")
                .append(request.getUsername())
                .append(" is added to the db")
                .toString();

        String customerUsername = request.getUsername();
        Customer customer=new Customer();
        customer.setUsername(customerUsername);
        customerService.addUser(customer);

        UserResponseAdded response = UserResponseAdded.newBuilder()
                .setInfo(info)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void customerGet(UserRequestToGet request, StreamObserver<UserResponseGet> responseObserver) {
        String username = request.getUsername();
        Customer foundUser=customerService.findByUsername(username);
        UserResponseGet response;
        if(foundUser==null)
        {
            response = UserResponseGet.newBuilder()
                    .setUsername("null")
                    .setId("null")
                    .build();
        }
        else
        {
            response = UserResponseGet.newBuilder()
                    .setUsername(foundUser.getUsername())
                    .setId(foundUser.getIdNumber().toString())
                    .build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

