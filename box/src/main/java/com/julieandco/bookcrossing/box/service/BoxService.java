package com.julieandco.bookcrossing.box.service;

import com.julieandco.bookcrossing.box.entity.Box;
import com.julieandco.bookcrossing.box.entity.dto.BookDTO;
import com.julieandco.bookcrossing.box.repo.BoxRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoxService {
    private final BoxRepo boxRepository;

    public BoxService(BoxRepo boxRepository) {
        this.boxRepository=boxRepository;
    }

    @Transactional
    public void addBox(Box box){
        if(boxRepository.findByAddress(box.getAddress()) == null){
            boxRepository.save(box);
        }
    }

    @Transactional
    public Box findByAddress(String address){
        return boxRepository.findByAddress(address);
    }


    @Transactional
    public void addBook(Box box, BookDTO receivedBook) {
        System.out.println("------SERVICE-----");
        Box foundBox = boxRepository.findByAddress(box.getAddress());
        System.out.println("BOXID: "+foundBox.getId().toString());
        System.out.println("BOOKID: "+receivedBook.getId().toString());

        Box newBox=new Box(foundBox.getId(), receivedBook.getId(), foundBox.getAddress());
        boxRepository.save(newBox);
        System.out.println("END OF ADDBOOK");

    }

    @Transactional
    public void checkOut(Box box, BookDTO book){
        System.out.println("BOOKSERvice checkout");
        Box removing = boxRepository.findByBook(book.getId());
        boxRepository.delete(removing);
    }
}
