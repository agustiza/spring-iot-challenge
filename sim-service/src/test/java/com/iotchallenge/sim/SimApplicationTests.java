package com.iotchallenge.sim;

import com.iotchallenge.sim.model.Sim;
import com.iotchallenge.sim.repository.SimRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SimApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	SimRepository repository;

	@Test
	void contextLoads() {
	}

	@Test
	void controllerReturns404onNonexistentSim() throws Exception {
		mockMvc.perform(get("/api/sims/{simId}", "1")
				).andExpect(status().is(NOT_FOUND.value()));
	}

	@Test
	void controllerReturnsSim() throws Exception {
		repository.save(new Sim("2", "spain", true));

		mockMvc.perform(get("/api/sims/{simId}", "2")
		).andExpect(status().isOk())
				.andExpect(jsonPath("$.simId").value("2"));
	}

}
