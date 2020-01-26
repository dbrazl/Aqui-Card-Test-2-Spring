package com.aquicard.src.Integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.aquicard.src.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void getController_ShouldReturnAListOfUsersWithHttpStatus200() throws Exception {
		this.mockMvc.perform(get("/user").contentType("application/json"))
			.andExpect(jsonPath("$").isArray())
			.andExpect(status().isOk());
	}
	
	@Test
	public void getByNameController_ShouldReturnHttpStatus400() throws Exception {
		this.mockMvc.perform(
				get("/user/getbyname")
				.contentType("aplication/json"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getByNameController_ShouldReturnHttpStatus404() throws Exception {
		this.mockMvc.perform(
				get("/user/getbyname")
				.contentType("aplication/json")
				.param("name", "Nome que não existe"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void getByNameController_ShouldReturnUserAndHttpStatus200() throws Exception {
		this.mockMvc.perform(
				get("/user/getbyname")
				.contentType("aplication/json")
				.param("name", "Daniel"))
			.andExpect(jsonPath("$").isArray())
			.andExpect(status().isOk());
	}
	
	@Test
	public void getByCodeController_ShouldReturnHttpStatus400() throws Exception {
		this.mockMvc.perform(
				get("/user/getbycode")
				.contentType("aplication/json"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getByCodeController_ShouldReturnHttpStatus404() throws Exception {
		this.mockMvc.perform(
				get("/user/getbycode")
				.contentType("aplication/json")
				.param("code", "0"))
			.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void getByCodeController_ShouldReturnUserAndHttpStatus200() throws Exception {
		String json = "{\"code\":1,\"name\":\"Daniel\",\"birthday\":\"02-10-1997\"}";
		
		this.mockMvc.perform(
				get("/user/getbycode")
				.contentType("aplication/json")
				.param("code", "1"))
			.andExpect(content().string(json))
			.andExpect(status().isOk());
	}
	
	@Test
	public void postController_ShouldReturnHttpStatus400_Name() throws Exception {
		User user = new User();
		user.setName(null);
		user.setBirthday("02-10-1997");
		
		this.mockMvc.perform(
				post("/user")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user))
				)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void postController_ShouldReturnHttpStatus400_Birthday() throws Exception {
		User user = new User();
		user.setName("Daniel");
		user.setBirthday(null);
		
		this.mockMvc.perform(
				post("/user")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user))
				)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void postController_ShouldReturnUserAndReturnHttpStatus200() throws Exception {	
		User user = new User();
		user.setName("Daniel");
		user.setBirthday("02-10-1997");
		
		this.mockMvc.perform(
				post("/user")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user))
				)
			.andExpect(jsonPath("$").exists())
			.andExpect(status().isOk());
	}
	
	@Test
	public void putController_ShouldReturnHttpStatus404_code() throws Exception {
		User newData = new User();
		newData.setName("Novo");
		newData.setBirthday("02-10-1997");
		
		this.mockMvc.perform(
				put("/user")
				.contentType("application/json")
				.param("code", "0")
				.content(objectMapper.writeValueAsString(newData))
				)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void putController_ShouldReturnHttpStatus400_code() throws Exception {
		User newData = new User();
		newData.setName("Novo");
		newData.setBirthday("02-10-1997");
		
		this.mockMvc.perform(
				put("/user")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(newData))
				)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putController_ShouldReturnHttpStatus400_Name() throws Exception {
		User newData = new User();
		newData.setName(null);
		newData.setBirthday("02-10-1997");
		
		this.mockMvc.perform(
				put("/user")
				.contentType("application/json")
				.param("code", "1")
				.content(objectMapper.writeValueAsString(newData))
				)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putController_ShouldReturnHttpStatus400_Birthday() throws Exception {
		User newData = new User();
		newData.setName("Novo");
		newData.setBirthday(null);
		
		this.mockMvc.perform(
				put("/user")
				.contentType("application/json")
				.param("code", "1")
				.content(objectMapper.writeValueAsString(newData))
				)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void putController_ShouldReturnUserAndReturnHttpStatus200() throws Exception {
		User newData = new User();
		newData.setName("Novo");
		newData.setBirthday("02-10-1997");
		
		this.mockMvc.perform(
				put("/user")
				.contentType("application/json")
				.param("code", "2")
				.content(objectMapper.writeValueAsString(newData))
				)
			.andExpect(jsonPath("$").exists())
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteControlle_ShoulReturnHttpStatus400() throws Exception {
		this.mockMvc.perform(
				delete("/user")
				.contentType("application/json")
				)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteController_ShouldReturnHttpStatus404() throws Exception {
		this.mockMvc.perform(
				delete("/user")
				.contentType("application/json")
				.param("code", "0")
				)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteController_ShouldReturnHttpStatus200() throws Exception {
		//Create user
		User user = new User();
		user.setName("User");
		user.setBirthday("02-10-1997");
		
		MvcResult result = this.mockMvc.perform(
				post("/user")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user))
				)
			.andReturn();
		
		String response = result.getResponse().getContentAsString();
		String[] data = response.split(",");
		String[] codeString = data[0].split(":");
		
		//Delete him
		this.mockMvc.perform(
				delete("/user")
				.contentType("application/json")
				.param("code", codeString[1])
				)
			.andExpect(status().isOk());
	}
}
