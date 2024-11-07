package com.example.streaming.batch.write;

import com.example.streaming.common.entity.UserViewLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class CustomItemWriter implements ItemWriter<Object> {

    @Override
    public void write(Chunk<? extends Object> chunk) throws Exception {

        log.info("write");

        for (Object item : chunk) {
//            log.info("Writing item: {}", item);

            if (item instanceof UserViewLogEntity) {
                UserViewLogEntity userViewLogEntity = (UserViewLogEntity) item;

                log.info("Writing item : {}", userViewLogEntity.toString());
            }
        }

    }
}
