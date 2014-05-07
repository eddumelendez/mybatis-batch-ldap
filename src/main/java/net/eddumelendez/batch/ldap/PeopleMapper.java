package net.eddumelendez.batch.ldap;

import java.util.List;

public interface PeopleMapper {
	
	List<People> findAll();
	
	void insertPeople(People people);

}
