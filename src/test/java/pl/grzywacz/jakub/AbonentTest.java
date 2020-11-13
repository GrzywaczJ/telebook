package pl.grzywacz.jakub;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.grzywacz.jakub.model.Abonent;
import pl.grzywacz.jakub.model.Address;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class AbonentTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addAbonentToDateBase() throws Exception {
        Abonent abonent = Abonent.builder().
                address(Address.builder()
                        .city("Poznan")
                        .street("Poznanska")
                        .zipCode("11-111")
                        .homeNumber("33a").build())
                .age(13)
                .firstName("Jan")
                .lastName("Nowak")
                .sex("M").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/abonent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(abonent)))
                .andExpect(status().isCreated());


    }

    @Test
    public void updateAbonentAPI() throws Exception
    {   Abonent abonent = Abonent.builder().
            address(Address.builder()
                    .city("Poznan")
                    .street("Poznanska")
                    .zipCode("11-111")
                    .homeNumber("33a").build())
            .age(13)
            .firstName("Jan")
            .lastName("Nowak")
            .sex("M").build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/abonent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(abonent)))
                .andExpect(status().isCreated())
                .andReturn();

        mvcResult.getResponse().getContentAsString();
        Abonent responseAbonent = stringAsObject(mvcResult.getResponse().getContentAsString(), Abonent.class);



        abonent.setFirstName("Janina");
        abonent.setLastName("Nowakowska");

        mockMvc.perform( MockMvcRequestBuilders
                .put("/abonent/{id}", responseAbonent.getId())
                .content(asJsonString(abonent))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Janina"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Nowakowska"));
    }

    @Test
    public void deleteAbonentAPI() throws Exception
    {   Abonent abonent = Abonent.builder().
            address(Address.builder()
                    .city("Poznan")
                    .street("Poznanska")
                    .zipCode("11-111")
                    .homeNumber("33a").build())
            .age(13)
            .firstName("Jan")
            .lastName("Nowak")
            .sex("M").build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/abonent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(abonent)))
                .andExpect(status().isCreated())
                .andReturn();

        Abonent responseAbonent = stringAsObject(mvcResult.getResponse().getContentAsString(), Abonent.class);



        mockMvc.perform( MockMvcRequestBuilders.delete("/abonent/{id}", responseAbonent.getId()) )
                .andExpect(status().isNoContent());
    }

    @Test
    public void searchAbonentAPI() throws Exception
    {   Abonent firstAbonent = Abonent.builder().
            address(Address.builder()
                    .city("Poznan")
                    .street("Poznanska")
                    .zipCode("11-111")
                    .homeNumber("33a").build())
            .age(13)
            .firstName("Jan")
            .lastName("Nowak")
            .sex("M").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/abonent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(firstAbonent)))
                .andExpect(status().isCreated())
                .andReturn();

        Abonent secondAbonent = Abonent.builder().
                address(Address.builder()
                        .city("Wroclaw")
                        .street("Poznanska")
                        .zipCode("12-122")
                        .homeNumber("55a").build())
                .age(14)
                .firstName("Janusz")
                .lastName("Kowalski")
                .sex("M").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/abonent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(secondAbonent)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResultOnlyOne = mockMvc.perform(MockMvcRequestBuilders.get("/abonent/search")
                .param("querryParam", "Kowal")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Abonent> abonentsOnlyOne = stringAsListOfObjects(mvcResultOnlyOne.getResponse().getContentAsString());
        assertThat(List.of(secondAbonent)).usingElementComparatorIgnoringFields("id","address").isEqualTo(abonentsOnlyOne);

        MvcResult mvcResultBoth = mockMvc.perform(MockMvcRequestBuilders.get("/abonent/search")
                .param("querryParam", "Jan")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Abonent> abonentsBoth = stringAsListOfObjects(mvcResultBoth.getResponse().getContentAsString());
        assertThat(List.of(secondAbonent,firstAbonent)).usingElementComparatorIgnoringFields("id","address").containsExactlyInAnyOrderElementsOf(abonentsBoth);

        MvcResult mvcResultNone = mockMvc.perform(MockMvcRequestBuilders.get("/abonent/search")
                .param("querryParam", "xDxDxD")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Abonent> abonentsNone = stringAsListOfObjects(mvcResultNone.getResponse().getContentAsString());
        assertThat(List.of()).isEqualTo(abonentsNone);




    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Abonent stringAsObject(final String object, final Class <Abonent> clazz) {
        try {
            return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(object,clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }public static List<Abonent> stringAsListOfObjects(final String object) {
        try {
            return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(object, new TypeReference<List<Abonent>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
