package com.org.CoronaReport;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.csv.CSVRecord;

@Entity
public class CovidData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String iso;
	private String continent;
	private String location;
	private LocalDate date;
	private int totalCase;
	private int newCase;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getTotalCase() {
		return totalCase;
	}
	public void setTotalCase(int totalCase) {
		this.totalCase = totalCase;
	}
	public int getNewCase() {
		return newCase;
	}
	public void setNewCase(int newCase) {
		this.newCase = newCase;
	}
	public void fillData(CSVRecord record) throws ParseException {
		this.iso = record.get("iso_code");
		this.continent = record.get("continent");
		this.location = record.get("location");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
        this.date = LocalDate.parse(record.get("date"), formatter);

		if(!record.get("total_cases").isBlank())
			this.totalCase = (int)Double.parseDouble(record.get("total_cases"));
		if(!record.get("new_cases").isBlank())
			this.newCase = (int)Double.parseDouble(record.get("new_cases"));
	}
	
	
	

}
