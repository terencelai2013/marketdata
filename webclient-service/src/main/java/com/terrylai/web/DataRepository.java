package com.terrylai.web;

import java.util.List;

public interface DataRepository {

	List<Name> getAllName();
	
	List<Data> getName(String symbol);
}
