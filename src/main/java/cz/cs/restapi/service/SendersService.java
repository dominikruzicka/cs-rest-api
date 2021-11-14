package cz.cs.restapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SendersService {

    public ResponseEntity<Object> getSendersList(){
        return new ResponseEntity("All OK BRO", HttpStatus.OK);
    }

}
