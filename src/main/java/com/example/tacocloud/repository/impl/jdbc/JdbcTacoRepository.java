package com.example.tacocloud.repository.impl.jdbc;

import com.example.tacocloud.model.Taco;
import com.example.tacocloud.repository.TacoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbc;
    private final PreparedStatementCreatorFactory ingredientInserter;
    private final PreparedStatementCreatorFactory tacoInserter;

    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        tacoInserter = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP
        );
        tacoInserter.setReturnGeneratedKeys(true);

        ingredientInserter = new PreparedStatementCreatorFactory(
                "insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
                Types.VARCHAR, Types.VARCHAR
        );
        ingredientInserter.setReturnGeneratedKeys(false);
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTaco(taco);
        taco.setId(tacoId);
        taco.getIngredients().forEach(i -> saveIngredient(tacoId, i));
        return taco;
    }

    private long saveTaco(Taco taco) {
        taco.setCreatedAt(new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = tacoInserter.newPreparedStatementCreator(Arrays.asList(
                taco.getName(), new Timestamp(taco.getCreatedAt().getTime())
        ));

        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    void saveIngredient(long tacoId, String ingredientId) {
        jdbc.update(ingredientInserter.newPreparedStatementCreator(Arrays.asList(
                tacoId, ingredientId
        )));
    }
}
