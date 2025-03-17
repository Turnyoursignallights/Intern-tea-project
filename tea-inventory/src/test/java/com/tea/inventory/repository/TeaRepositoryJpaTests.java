package com.tea.inventory.repository;

import com.tea.inventory.product.entity.Tea;
import com.tea.inventory.product.repository.TeaRepositoryJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TeaRepositoryJpaTests {
    private static final Logger logger = LoggerFactory.getLogger(TeaRepositoryJpaTests.class);
    private TeaRepositoryJpa teaJpa;

    @Autowired
    public TeaRepositoryJpaTests(TeaRepositoryJpa teaJpa) {
        this.teaJpa = teaJpa;
    }

    @Test
    public void TeaRepository_saveAll_ReturnSavedTea() {
        //Arrange
        Tea newTea = Tea.builder()
                .name("New tea")
                .type("New tea type")
                .build();
        //Act
        Tea savedTea = teaJpa.save(newTea);
        //Assert
        assertThat(savedTea).isNotNull();
        assertThat(savedTea.getId()).isGreaterThan(0);
    }

    @Test
    public void TeaRepository_GetAll_ReturnMoreThenOneTea() {
        Tea tea1 = Tea.builder()
                .name("tea1")
                .type("tea1 type").build();
        Tea tea2 = Tea.builder()
                .name("tea2")
                .type("tea2 type").build();

        teaJpa.save(tea1);
        teaJpa.save(tea2);
        List<Tea> teaList = teaJpa.findAll();

        Assertions.assertThat(teaList).isNotNull();
        Assertions.assertThat(teaList.size()).isEqualTo(2);
    }

    @Test
    public void TeaRepository_GetOneID_ReturnFindIDTea() {
        Tea newTea = Tea.builder()
                .name("New tea")
                .type("New tea Type")
                .build();
        teaJpa.save(newTea);

        //jpa will pass back the id to newTea
        Optional<Tea> findTea = teaJpa.findById(newTea.getId());
        Assertions.assertThat(findTea).isNotNull();
    }

    @Test
    public void TeaRepository_UpdateTea_ReturnTeaNotNull() {

        Tea newTea = Tea.builder()
                .name("New tea")
                .type("New tea type").build();

        teaJpa.save(newTea);
        Tea teaSaved = teaJpa.findById(newTea.getId()).get();
        logger.info(teaSaved.getType());
        logger.info(teaSaved.getName());
        teaSaved.setName("Update Tea");
        teaSaved.setType("Update Tea Type");
        Tea updateTea = teaJpa.save(teaSaved);
        logger.info(updateTea.getName());
        logger.info(updateTea.getType());


        Assertions.assertThat(updateTea).isNotNull();
        Assertions.assertThat(updateTea.getType()).isNotNull();
        Assertions.assertThat(updateTea.getName()).isNotNull();
    }

    @Test
    public void TeaRepository_DeleteById_ReturnTeaIsNull() {
        Tea newTea = Tea.builder()
                .name("new tea")
                .type("new tea type")
                .build();
        teaJpa.save(newTea);

        teaJpa.deleteById(newTea.getId());
        Optional<Tea> teaReturn = teaJpa.findById(newTea.getId());

        Assertions.assertThat(teaReturn).isEmpty();

    }


}
