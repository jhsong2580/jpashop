package jpashop.jpashop.acceptance.utils;

import com.google.common.base.CaseFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleanup implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<String, String> idColumnNamePerTable = new HashMap<>();
    private List<String> nonidTable = new LinkedList<>();

    @Override
    public void afterPropertiesSet() {
        idColumnNamePerTable = entityManager.getMetamodel().getEntities().stream()
            .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
            .filter(e -> e.getJavaType().getAnnotation(Table.class) == null)
            .collect(Collectors.toMap(
                e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()),
                e -> {
                    try {
                        Column id = e.getJavaType().getDeclaredField("id")
                            .getAnnotation(Column.class);
                        return id != null ? id.name() : "id";
                    } catch (NoSuchFieldException ex) {
                        nonidTable.add(e.getName());
                        return "";
                    }
                }
            ));


    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (EntityType<?> entity : entityManager.getMetamodel().getEntities()) {
            String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                entity.getName());
            String colunmName = "";


            if (entity.getJavaType().getAnnotation(Entity.class) == null) {
                continue;
            }
            if (entity.getJavaType().getAnnotation(Table.class) != null) {
                if (!ObjectUtils.isEmpty(entity.getJavaType().getAnnotation(Table.class).name())) {
                    tableName = entity.getJavaType().getAnnotation(Table.class).name();
                }
            }
            entityManager.createNativeQuery("delete from " + tableName).executeUpdate();
            try {
                GeneratedValue generatedValue = entity.getJavaType().getDeclaredField("id")
                    .getAnnotation(GeneratedValue.class);

                if (generatedValue.strategy()
                    == GenerationType.SEQUENCE) {
                    String sequenceName = entity.getJavaType()
                        .getAnnotation(SequenceGenerator.class).sequenceName();
                    entityManager.createNativeQuery(
                        "ALTER SEQUENCE "+sequenceName+"  RESTART WITH 1"
                    ).executeUpdate();
                } else if (generatedValue.strategy()
                    == GenerationType.TABLE) {
                    entityManager.createNativeQuery(
                            "update " + generatedValue.generator() + " set next_val=0;")
                        .executeUpdate();
                } else if (generatedValue.strategy()
                    == GenerationType.IDENTITY) {
                    Column id = entity.getJavaType().getDeclaredField("id")
                        .getAnnotation(Column.class);
                    String key_name = id != null ? id.name() : "id";

                    entityManager.createNativeQuery(
                        "ALTER TABLE " + tableName + " ALTER COLUMN " + key_name
                            + " RESTART WITH 1").executeUpdate();

                }

            } catch (NoSuchFieldException e) {
                continue;
            }


        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
