package com.innosoft.webreservation.dao;

import java.util.List;

import com.innosoft.webreservation.entity.MstCode;

/**
 *CRUD interface for code data object.
 */
public interface CodeDao {
	/**
	 * List code method
	 * @return
	 */
	public List<MstCode> listCode();
	/**
	 * List code by king method 
	 * @param kind
	 * @return
	 */
	public List<MstCode> listCodeByKind(String kind);
	/**
	 * Add code method
	 * @param code
	 * @return
	 */
	public MstCode addCode(MstCode code);
	/**
	 * Edit code method
	 * @param code
	 * @return
	 */
	public MstCode editCode(MstCode code);
	/**
	 * Delete code method
	 * @param id
	 * @return
	 */
	public boolean deleteCode(int id);
}
