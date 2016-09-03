package me.hncn.dhtspider.repository.mongodb.dao.base.baseMongoDao;

import com.mongodb.WriteResult;
import me.hncn.dhtspider.util.Pagination;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by 001 on 2016/7/12.
 */
@NoRepositoryBean
public interface MongodbBaseDAO<T> {
    List<T> find(Query query);
    T findOne(Query query);
    T update(Query query, Update update);
    T save(T entity);
    Pagination<T> findPage(Pagination<T> page,Query query);
    WriteResult removeAll();
    WriteResult remove(Query query);
    WriteResult remove(String id);
    T findById(String id);
    long count(Query query);
    Class<T> getEntityClass();
}
