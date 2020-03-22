package com.iwhalecloud.retail.common.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Created by xh on 2019/5/15.
 */
@Service
@ConditionalOnBean(value = RestHighLevelClient.class)
public class ElasticSearchService {

    @Autowired
    private RestHighLevelClient client;

    public void createIndex(String indexName, Object o) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.mapping("_doc", o);

        //属性 比如分片数、副本数、分词器等等<br>
        Settings.Builder builder = Settings.builder().put("index.number_of_shards", "2")
                .put("index.number_of_replicas", "2");
        request.settings(builder);
    }


    public void batchPutDoc(String index, String type, Map<String, Map<String, Object>> docMaps) {
        BulkRequest request = new BulkRequest();
        docMaps.forEach((id, docMap) -> {
            request.add(new IndexRequest(index, type, id).source(docMap));
        });
    }
}
