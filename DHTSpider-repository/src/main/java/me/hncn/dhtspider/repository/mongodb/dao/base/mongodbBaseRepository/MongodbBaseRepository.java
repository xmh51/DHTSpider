package me.hncn.dhtspider.repository.mongodb.dao.base.mongodbBaseRepository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MongodbBaseRepository<T,ID extends Serializable> extends MongoRepository<T, ID>
{
}
