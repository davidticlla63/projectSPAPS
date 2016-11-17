package com.teds.spaps.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.teds.spaps.util.Time;

/**
 * 
 * @author mauriciobejaranorivera
 * 
 */
@Stateless
public abstract class DataAccessObject<T> {

	@PersistenceContext(name = "primary")
	private EntityManager em;

	private @Inject Event<Object> objectEventSrc;

	@SuppressWarnings("unused")
	private EntityTransaction tx = null;

	private Class<T> typeT;

	/**
	 * Default constructor
	 * 
	 */
	public DataAccessObject() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            entity class
	 */
	public DataAccessObject(Class<T> typeT) {
		this.typeT = typeT;
	}
	
	

	/**
	 * 
	 * @param message  of result operation
	 */
	public void println(String message){
		//System.out.println( message);
	}

	public T create(T entity) {
		this.em.persist(entity);
		this.em.flush();
		this.em.refresh(entity);
		objectEventSrc.fire(entity);
		return entity;
	}

	public T update(T entity) {
		T t = (T) this.em.merge(entity);
		return t;
	}

	public boolean delete(Object id) {
		return true;
	}

	public void deleteReal(T entity) {
		T t = this.em.merge(entity);
		this.em.remove(t);
	}

	public void beginTransaction() {
		// if(tx==null){
		// tx = em.getTransaction();
		// // }
		// if (!tx.isActive()) {
		// tx.begin();
		// }
	}

	public void commitTransaction() {
		// tx.commit();
	}

	public void rollbackTransaction() {
		// if (!tx.isActive()) {
		// tx.rollback();
		// }
	}

	public T findById(Object id) {
		return null;
	}

	public List<T> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<T> executeQueryResulList(String query) {
		Query q = em.createQuery(query);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public T executeQuerySingleResult(String query) {
		Query q = em.createQuery(query);
		return (T) q.getSingleResult();
	}

	/**
	 * Obtiene el ultimo registro con estado activo('AC') de la tabla
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findLastActiveRecord() {
		Query q = em.createQuery(
				"SELECT em FROM " + this.typeT.getSimpleName()
						+ " em  order by em.id desc").setMaxResults(1);
		return (T) q.getSingleResult();
	}

	public Double executeNativeQuerySingleResultDouble(String query) {
		Query q = em.createNativeQuery(query);
		Double val = (Double) q.getSingleResult();
		return val;
	}

	/**
	 * Obtiene el ultimo registro con estado activo('AC') de la tabla
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findLastActiveRecord(String param, Object value) {
		Query q = em.createQuery(
				"SELECT em FROM " + this.typeT.getSimpleName()
						+ " em  where em." + param + "=" + value
						+ " order by em.id desc").setMaxResults(1);
		return (T) q.getSingleResult();
	}

	public T findByParameterObjectTwo(String parameter, String parameterTwo,
			Object valor, Object valorTwo) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get(parameterTwo), valorTwo));
		return em.createQuery(criteria).getSingleResult();
	}

	public T findByParameterObjectTwoQueryOr(String parameter,
			String parameterTwo, Object valor, Object valorTwo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(
				cb.or(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo)));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<T> findAllByParameterObjectTwoQueryOr(String parameter,
			String parameterTwo, Object valor, Object valorTwo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(
				cb.or(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo)));
		return em.createQuery(criteria).getResultList();
	}

	public List<T> findAllActiveByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get("estado"), "AC"));
		return em.createQuery(criteria).getResultList();
	}

	public List<T> findDescAllOrderedByParameter(String parameter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).orderBy(cb.desc(object.get(parameter)));
		return em.createQuery(criteria).getResultList();
	}

	public List<T> findAscAllOrderedByParameter(String parameter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).orderBy(cb.asc(object.get(parameter)));
		return em.createQuery(criteria).getResultList();
	}

	public List<T> findAllInactiveByParameter(String parameter, Object valor) {
		return null;
	}

	public List<T> findAllByEstadoOrderByAsc(String estado) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get("estado"), estado))
				.orderBy(cb.asc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	public List<T> findAllByEstadoOrderByDesc(String estado) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get("estado"), estado))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	public T findByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor));
		return em.createQuery(criteria).getSingleResult();
	}

	public BigInteger countTotalRecord() {
		String query = "select count(em) from " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' or em.estado='IN' ";
		println("query:" + query);
		return (BigInteger) em.createNativeQuery(query).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllBySize(int start, int maxRows) {
		Query q = em
				.createQuery("SELECT em FROM "
						+ this.typeT.getSimpleName()
						+ " em where em.estado='AC' or em.estado='IN' order by em.id desc");
		q.setFirstResult(start);
		q.setMaxResults(maxRows);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActivosByMaxResultOrderByDesc(int maxRows) {
		Query q = em.createQuery("SELECT em FROM " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' order by em.id desc");
		q.setMaxResults(maxRows);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActivosByMaxResultOrderByAsc(int maxRows) {
		Query q = em.createQuery("SELECT em FROM " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' order by em.id desc");
		q.setMaxResults(maxRows);
		return q.getResultList();
	}

	public List<T> findAllByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor));
		return em.createQuery(criteria).getResultList();
	}

	public List<T> findAllActivosByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get("estado"), "AC"));
		return em.createQuery(criteria).getResultList();
	}

	public List<T> findAllByParameterObjectTwo(String parameter,
			String parameterTwo, Object valor, Object valorTwo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object)
				.where(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActivosByQuery(String parameter, Object valor) {
		String upperQuery = valor.toString().toUpperCase();
		Query q = em
				.createQuery("SELECT em FROM "
						+ this.typeT.getSimpleName()
						+ " em where upper(translate(em."
						+ parameter
						+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
						+ upperQuery + "%' and em.estado='AC' order by em."
						+ parameter + " asc");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActivosByQueryAndTwoParameter(String parameter1,
			Object value1, String parameter2, Object value2) {
		String upperQuery = value2.toString().toUpperCase();
		String qu = "SELECT em FROM "
				+ this.typeT.getSimpleName()
				+ " em where upper(translate(em."
				+ parameter2
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ upperQuery + "%' and em." + parameter1 + "='" + value1
				+ "'  and em.estado='AC' order by em." + parameter2 + " desc";
		println(qu);
		Query q = em.createQuery(qu);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveInactiveOtherTableAndParameter(
			String nameTableObject, String nameTableQuery, String paramter,
			Object value) {
		String query = "select em from " + this.typeT.getSimpleName() + " em,"
				+ nameTableObject
				+ " gcc where (em.estado='AC' or em.estado='IN') and em."
				+ nameTableQuery + ".id=gcc.id and gcc." + paramter + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	public List<T> findAllByParameterDate(String parameter, Date valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	public T findAllByParameterDateAndTwoParameter(String parameter,
			Date valor, String parameterTwo, Object valueTwo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object)
				.where(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valueTwo))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getSingleResult();
	}

	/**
	 * 
	 * @param paramter
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllActiveParameter(String paramter, Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where (em.estado='AC' OR em.estado='IN') and  em."
				+ paramter + ".id=" + value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findActiveParameter(String paramter, Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' and  em." + paramter + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOtherTableAndParameter(String nameTableObject,
			String nameTableQuery, String paramter, Object value) {
		String query = "select em from " + this.typeT.getSimpleName() + " em,"
				+ nameTableObject + " gcc where em.estado='AC' and em."
				+ nameTableQuery + ".id=gcc.id and gcc." + paramter + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOtherTableAndParameterForName(
			String nameColumn, String valueColumn, String tableNameParam,
			Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where em.estado='AC' and em." + nameColumn + " like '"
				+ valueColumn + "' and  em." + tableNameParam + ".id=" + value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOtherTableAndParameterForNameAutoComplete(
			String nameColumn, String valueColumn, String nameColState,
			String valueState, String tableNameParam, Object value) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ "='"
				+ valueState
				+ "' and  upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn + "%'  and  em." + tableNameParam + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
			String nameColumn, String valueColumn, String nameColState,
			String valueState, String tableNameParam) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ "='"
				+ valueState
				+ "' and  upper(translate(em."
				+ tableNameParam
				+ "."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn + "%'";
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllAndParameterForNameAutoComplete(String nameColumn,
			String valueColumn) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where "
				+ " upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn + "%' ";
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	public boolean findAllOtherTableAndParameterForName(String nameColumn,
			String valueColumn, String tableNameParam, Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where em.estado='AC' and  em." + nameColumn + " like '"
				+ valueColumn + "' and  em." + tableNameParam + ".id=" + value;
		println("Query " + query);
		return em.createQuery(query).getResultList().size() > 0;
	}

	@SuppressWarnings("unchecked")
	public T findAllOther2TableAndParameterForName(String nameColumn,
			String valueColumn, String tableNameParam, Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where em.estado='AC' and  em." + nameColumn + " like '"
				+ valueColumn + "' and  em." + tableNameParam + ".id=" + value;
		println("Query " + query);
		return (T) em.createQuery(query).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveParameterOrderedForId(String paramter,
			Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where (em.estado='AC' OR em.estado='IN') and  em."
				+ paramter + ".id=" + value + " order by em.id asc";
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	/**
	 * 
	 * @param parameterUsuer
	 * @param usuario
	 * @param nameTableQuery
	 * @param value1
	 * @param nameTable2Query
	 * @param value2
	 * @param paramterBegin
	 * @param fechaini
	 * @param paramterEnd
	 * @param fechafin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findActiveForThwoDatesAndThwoObject(String parameterUsuer,
			String usuario, String nameTableQuery, Object value1,
			String nameTable2Query, Object value2, String paramterBegin,
			Date fechaini, String paramterEnd, Date fechafin) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where  em." + nameTableQuery + ".id=" + value1
				+ " and  em." + nameTable2Query + ".id=" + value2 + " and (em."
				+ parameterUsuer + " like '" + usuario + "' or em."
				+ parameterUsuer + " like '" + usuario
				+ "') and  to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '999999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value1, String nameTable2Query,
			Object value2, String paramterBegin, Date fechaini,
			String paramterEnd, Date fechafin) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where  em." + nameTableQuery + ".id=" + value1
				+ " and  em." + nameTable2Query + ".id=" + value2
				+ " and to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '999999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findActiveForThwoDatesAndThwoObject(String parameterUsuer,
			String usuario, String nameTableQuery, Object value,
			String paramterBegin, Date fechaini, String paramterEnd,
			Date fechafin) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where   em." + nameTableQuery + ".id=" + value
				+ " and (em." + parameterUsuer + " like '" + usuario
				+ "' or em." + parameterUsuer + " like '" + usuario
				+ "') and  to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '999999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value, String paramterBegin,
			Date fechaini, String paramterEnd, Date fechafin) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where   em." + nameTableQuery + ".id=" + value
				+ " and  to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '999999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value, String paramterBegin,
			Date fechaini, String paramterEnd, Date fechafin,
			String paramEstado, Object estadoObject) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where   em." + nameTableQuery + ".id=" + value
				+ " and  to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '999999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin) + " and em."
				+ paramEstado + "=" + estadoObject + "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	public boolean findByQuery(String parameter, Object valor) {
		String upperQuery = valor.toString().toUpperCase();
		Query q = em.createQuery("SELECT em FROM " + this.typeT.getSimpleName()
				+ " em where upper(translate(em." + parameter
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '"
				+ upperQuery + "' and em.estado='AC' order by em." + parameter
				+ " asc");
		return q.getResultList().size() > 0;
	}

	@SuppressWarnings("unchecked")
	public T findByQuerys(String parameter, Object valor) {
		String upperQuery = valor.toString().toUpperCase();
		Query q = em.createQuery("SELECT em FROM " + this.typeT.getSimpleName()
				+ " em where upper(translate(em." + parameter
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '"
				+ upperQuery + "' and em.estado='AC' order by em." + parameter
				+ " asc");
		return (T) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<String> findByRowUnique(String rowName) {
		try {
			String query = "select distinct fac." + rowName + " from "
					+ this.typeT.getSimpleName() + " fac";
			println(query);
			return em.createQuery(query).getResultList();

		} catch (Exception e) {
			println("Error en traerGestionesFacturadas: "
					+ e.getMessage());
			return null;
		}
	}

	public EntityManager getEntityManager() {
		return em;
	}

}