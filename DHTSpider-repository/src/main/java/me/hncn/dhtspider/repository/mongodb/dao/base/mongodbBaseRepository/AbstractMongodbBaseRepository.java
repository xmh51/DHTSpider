package me.hncn.dhtspider.repository.mongodb.dao.base.mongodbBaseRepository;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * @author preideas
 *Mongodb Repository基类，注入MongoTemplate
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public abstract class AbstractMongodbBaseRepository<T, ID extends Serializable>
{
    @Autowired
    protected MongoTemplate mongoTemplate;
}
