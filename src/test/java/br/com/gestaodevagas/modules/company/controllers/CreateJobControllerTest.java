package br.com.gestaodevagas.modules.company.controllers;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gestaodevagas.exceptions.CompanyNotFoundException;
import br.com.gestaodevagas.modules.company.dto.CreateJobDTO;
import br.com.gestaodevagas.modules.company.entities.CompanyEntity;
import br.com.gestaodevagas.modules.company.repositories.CompanyRepository;
import br.com.gestaodevagas.modules.utils.TestUtils;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private CompanyRepository companyRepository;

        @Before
        public void setup() {
                mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(SecurityMockMvcConfigurers.springSecurity())
                                .build();
        }

        @Test
        public void should_create_job() throws Exception {

                var company = CompanyEntity.builder()
                                .description("Empresa de laticínios")
                                .email("empresalati@gmail.com")
                                .name("Lati Lati")
                                .id(UUID.randomUUID())
                                .password("123456789")
                                .username("lati_lati")
                                .build();

                company = companyRepository.saveAndFlush(company);

                CreateJobDTO createJobDTO = CreateJobDTO.builder()
                                .description("Analista de Sistemas")
                                .benefits("Vale-refeição")
                                .level("Pleno")
                                .build();

                var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.convertObjectToJson(createJobDTO))
                                .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        }

        public void should_not_create_job_if_company_not_exists() throws Exception {
                CreateJobDTO createJobDTO = CreateJobDTO.builder()
                                .description("Analista de Sistemas")
                                .benefits("Vale-refeição")
                                .level("Pleno")
                                .build();

                
                        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(TestUtils.convertObjectToJson(createJobDTO))
                                        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

}
