package ir.bigz.spring.quratz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class DataImpl implements Data{

    @Override
    public void getData() {
        System.out.println("salam cheturi");
    }
}
