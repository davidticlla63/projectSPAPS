package com.teds.spaps.interfaces.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IDataAccessObject<T> {

	/**
	 * Stores an instance of the entity class in the database
	 * 
	 * @param entity
	 * @return T
	 * @throws Exception
	 */
	T create(T entity) throws Exception;

	/**
	 * Update the entity instance
	 * 
	 * @param <T>
	 * @param entity
	 * @return the object that is updated
	 * @throws Exception
	 */
	T update(T entity) throws Exception;

	/**
	 * Remove the record that is associated with the entity instance
	 * 
	 * @param id
	 * @return boolean
	 * @throws Exception
	 */
	boolean delete(Object id) throws Exception;

	void deleteReal(T entity) throws Exception;

	/**
	 * Init Transaction
	 */
	void beginTransaction();

	/**
	 * Commit Transaction
	 */
	void commitTransaction();

	/**
	 * Rollback Transaction
	 */
	void rollbackTransaction();

	/**
	 * executeQueryResulList
	 * 
	 * @param query
	 * @return List<T>
	 */
	List<T> executeQueryResulList(String query);

	/**
	 * executeQuerySingleResult
	 * 
	 * @param query
	 * @return T
	 */
	T executeQuerySingleResult(String query);

	/**
	 * findById
	 * 
	 * @param id
	 * @return T
	 */
	T findById(Object id);

	/**
	 * findByParameterObjectTwo
	 * 
	 * @param parameter
	 * @param parameterTwo
	 * @param valor
	 * @param valorTwo
	 * @return T
	 */
	T findByParameterObjectTwo(String parameter, String parameterTwo,
			Object valor, Object valorTwo);

	T findByParameterObject(String parameter, Object valor);

	/**
	 * Query two parameter Or
	 * 
	 * @param parameter
	 * @param parameterTwo
	 * @param valor
	 * @param valorTwo
	 * @return
	 */
	T findByParameterObjectTwoQueryOr(String parameter, String parameterTwo,
			Object valor, Object valorTwo);

	/**
	 * findAllByParameterObjectTwoQueryOr
	 * 
	 * @param parameter
	 * @param parameterTwo
	 * @param valor
	 * @param valorTwo
	 * @return List<T>
	 */
	List<T> findAllByParameterObjectTwoQueryOr(String parameter,
			String parameterTwo, Object valor, Object valorTwo);

	/**
	 * findAllActiveByParameter
	 * 
	 * @param parameter
	 * @param valor
	 * @return List<T>
	 */
	List<T> findAllActiveByParameter(String parameter, Object valor);

	/**
	 * findAllInactiveByParameter
	 * 
	 * @param parameter
	 * @param valor
	 * @return List<T>
	 */
	List<T> findAllInactiveByParameter(String parameter, Object valor);

	/**
	 * findDescAllOrderedByParameter
	 * 
	 * @param parameter
	 * @return List<T>
	 */
	List<T> findDescAllOrderedByParameter(String parameter);

	/**
	 * findAscAllOrderedByParameter
	 * 
	 * @param parameter
	 * @return List<T>
	 */
	List<T> findAscAllOrderedByParameter(String parameter);

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<T> findAll() throws Exception;

	/**
	 * 
	 * @param estado
	 * @return
	 */
	List<T> findAllByEstadoOrderByAsc(String estado);

	/**
	 * 
	 * @param estado
	 * @return
	 */
	List<T> findAllByEstadoOrderByDesc(String estado);

	/**
	 * Returns the number of total records
	 * 
	 * @param namedQueryName
	 * @return Long
	 */
	BigInteger countTotalRecord();

	/**
	 * 
	 * @param size
	 * @return
	 */
	List<T> findAllBySize(int start, int maxRows);

	/**
	 * 
	 * @param parameter
	 * @param valor
	 * @return
	 */
	T findByParameter(String parameter, Object valor);

	/**
	 * 
	 * @param parameter
	 * @param valor
	 * @return
	 */
	List<T> findAllByParameter(String parameter, Object valor);

	/**
	 * 
	 * @param parameter
	 * @param parameterTwo
	 * @param valor
	 * @param valorTwo
	 * @return
	 */
	List<T> findAllByParameterObjectTwo(String parameter, String parameterTwo,
			Object valor, Object valorTwo);

	/**
	 * 
	 * @param maxRows
	 * @return
	 */
	List<T> findAllActivosByMaxResultOrderByDesc(int maxRows);

	/**
	 * 
	 * @param maxRows
	 * @return
	 */
	List<T> findAllActivosByMaxResultOrderByAsc(int maxRows);

	/**
	 * 
	 * @param parameter
	 * @param valor
	 * @return
	 */
	List<T> findAllActivosByQuery(String parameter, Object valor);

	/**
	 * 
	 * @param parameter
	 * @param valor
	 * @return
	 */
	List<T> findAllActivosByParameter(String parameter, Object valor);

	/**
	 * 
	 * @param parameter1
	 * @param value
	 * @param parameter2
	 * @param valor
	 * @return
	 */
	List<T> findAllActivosByQueryAndTwoParameter(String parameter1,
			Object value, String parameter2, Object valor);

	/**
	 * 
	 * @param nameTableObject
	 * @param nameTableQuery
	 * @param paramter
	 * @param value
	 * @return
	 */
	List<T> findAllActiveInactiveOtherTableAndParameter(String nameTableObject,
			String nameTableQuery, String paramter, Object value);

	/**
	 * 
	 * @param parameter
	 * @param valor
	 * @return
	 */
	List<T> findAllByParameterDate(String parameter, Date valor);

	/**
	 * 
	 * @param parameter
	 * @param valor
	 * @param parameterTwo
	 * @param valueTwo
	 * @return
	 */
	T findAllByParameterDateAndTwoParameter(String parameter, Date valor,
			String parameterTwo, Object valueTwo);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @return
	 */
	T findByParameterObjectThree(String parameter, String parameterTwo,
			String parameterThree, Object valor, Object valorTwo,
			Object valorThree);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param parameterFour
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @param valorFour
	 * @return
	 */
	T findByParameterObjectFour(String parameter, String parameterTwo,
			String parameterThree, String parameterFour, Object valor,
			Object valorTwo, Object valorThree, Object valorFour);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @return
	 */
	List<T> findAllByParameterObjectThreeQueryOr(String parameter,
			String parameterTwo, String parameterThree, Object valor,
			Object valorTwo, Object valorThree);

	/**
	 * @param parameter
	 * @param parametertwo
	 * @param valor
	 * @param valorTwo
	 * @return
	 */
	List<T> findAllActivosByParameterTwo(String parameter, String parameterTwo,
			Object valor, Object valorTwo);

	/**
	 * @param parameter
	 * @param valor
	 * @param parameterTwo
	 * @param valorTwo
	 * @return
	 */
	List<T> findAllActiveByParameter(String parameter, Object valor,
			String parameterTwo, Object valorTwo);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param parameterFour
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @param valorFour
	 * @return
	 */
	List<T> findAllByParameterObjectFourQueryOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			Object valor, Object valorTwo, Object valorThree, Object valorFour);

	/**
	 * @param nameField
	 * @param tableFist
	 * @param value
	 * @return
	 */
	T findMaxTransferencia(String fieldMax, String parameter,
			String parameterTwo, Object value, Object valueTwo);

	/**
	 * @param nameField
	 * @param parameter
	 * @param parameterTwo
	 * @param value
	 * @param valueTwo
	 * @return
	 */
	Object findCount(String nameField, String parameter, String parameterTwo,
			Object value, Object valueTwo);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @return
	 */
	List<T> findAllByParameterObjectThree(String parameter,
			String parameterTwo, String parameterThree, Object valor,
			Object valorTwo, Object valorThree);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param parameterFour
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @param valorFour
	 * @return
	 */
	List<T> findAllByParameterObjectFour(String parameter, String parameterTwo,
			String parameterThree, String parameterFour, Object valor,
			Object valorTwo, Object valorThree, Object valorFour);

	/**
	 * @param nameField
	 * @param parameter
	 * @param value
	 * @return
	 */
	Object findCount(String nameField, String parameter, Object value);

	/**
	 * @param nameColumn
	 * @param valueColumn
	 * @param nameColumn1
	 * @param valueColumn1
	 * @param nameColumn2
	 * @param valueColumn2
	 * @param nameColState
	 * @param valueState
	 * @param tableNameParam
	 * @param value
	 * @return
	 */
	List<T> findAllActiveOtherTableAndParameterForNameAutoComplete(
			String nameColumn, String valueColumn, String nameColumn1,
			String valueColumn1, String nameColumn2, String valueColumn2,
			String nameColState, String valueState, String tableNameParam,
			Object value);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param parameterFour
	 * @param parameterFive
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @param valorFour
	 * @param valorFive
	 * @return
	 */
	List<T> findAllByParameterObjectFiveOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			String parameterFive, Object valor, Object valorTwo,
			Object valorThree, Object valorFour, Object valorFive);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param parameterFour
	 * @param parameterFive
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @param valorFour
	 * @param valorFive
	 * @return
	 */
	List<T> findAllByParameterObjectFiveOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			String parameterFive, String valor, String valorTwo,
			String valorThree, String valorFour, String valorFive);

	/**
	 * @param parameter
	 * @param parameterTwo
	 * @param parameterThree
	 * @param parameterFour
	 * @param valor
	 * @param valorTwo
	 * @param valorThree
	 * @param valorFour
	 * @return
	 */
	List<T> findAllByParameterObjectFourOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			String valor, String valorTwo, String valorThree, String valorFour);

	/**
	 * @param nameField
	 * @param parameter
	 * @param parameterTwo
	 * @param value
	 * @param valueTwo
	 * @return
	 */
	Object findCountEvolucionHM(String nameField, String parameter,
			String parameterTwo, Object value, Object valueTwo);
}
