package me.hncn.dhtspider.repository.mongodb.dao.base.baseMongoDao;

import java.util.List;

import com.mongodb.WriteResult;
import me.hncn.dhtspider.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract class MongodbBaseDAOImpl<T> implements MongodbBaseDAO<T> {

    private static final int DEFAULT_SKIP = 0;
    private static final int DEFAULT_LIMIT = 200;

    /**
     * spring mongodb　集成操作类　
     */
    @Autowired
    protected MongoTemplate mongoTemplate;


    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }
    public List<T> findAll() {
        return mongoTemplate.find(new Query(), this.getEntityClass());
    }


    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }


    public T update(Query query, Update update) {
        return mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }


    public T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }
    public WriteResult removeAll() {
        return mongoTemplate.remove(new Query(), this.getEntityClass());
    }
    public WriteResult remove(Query query) {
        return mongoTemplate.remove(query, this.getEntityClass());
    }
    public WriteResult remove(String id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)),this.getEntityClass());
    }


    public T findById(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    public Pagination<T> findPage(Pagination<T> page,Query query){
        long count = this.count(query);
        page.setTotalRecords(count);
        int pageNumber = page.getCurrentPage();
        int pageSize = page.getPageSize();
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        page.setPageList(rows);
        return page;
    }


    public long count(Query query){
        return mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 获取需要操作的实体类class
     *
     * @return
     */
    public abstract Class<T> getEntityClass();


}
