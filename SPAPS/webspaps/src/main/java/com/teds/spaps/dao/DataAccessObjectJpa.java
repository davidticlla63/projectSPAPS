package com.teds.spaps.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.teds.spaps.interfaces.dao.IDataAccessObject;
import com.teds.spaps.util.Time;

/**
 * 
 * @author mauriciobejaranorivera
 * 
 */
@Stateless
public abstract class DataAccessObjectJpa<T> implements IDataAccessObject<T> {

	// private @Inject EntityManager em;

	@PersistenceContext(name = "primary")
	private EntityManager em;

	private @Inject Event<Object> objectEventSrc;

	// private EntityTransaction tx = null;

	private Class<T> typeT;

	/**
	 * Default constructor
	 * 
	 */
	public DataAccessObjectJpa() {
		super();
	}

	/**
	 * 
	 * @param message
	 *            of result operation
	 */
	public void println(String message) {
		System.out.println(message);
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            entity class
	 */
	public DataAccessObjectJpa(Class<T> typeT) {
		this.typeT = typeT;
	}

	@Override
	public T create(T entity) {
		this.em.persist(entity);
		this.em.flush();
		this.em.refresh(entity);
		objectEventSrc.fire(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		T t = (T) this.em.merge(entity);
		return t;
	}

	@Override
	public boolean delete(Object id) {
		return true;
	}

	@Override
	public void deleteReal(T entity) {
		T t = this.em.merge(entity);
		this.em.remove(t);
	}

	@Override
	public void beginTransaction() {
		// if(tx==null){
		// tx = em.getTransaction();
		// }
		// if (!tx.isActive()) {
		// tx.begin();
		// }
	}

	@Override
	public void commitTransaction() {
		// tx.commit();
	}

	@Override
	public void rollbackTransaction() {
		// System.out.println("tx: " + tx);
		// if(tx!=null){
		// if (!tx.isActive()) {
		// tx.rollback();
		// }
		// }
	}

	@Override
	public T findById(Object id) {
		return em.find(this.typeT, id);
	}

	@Override
	public List<T> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> executeQueryResulList(String query) {
		Query q = em.createQuery(query);
		return q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T executeQuerySingleResult(String query) {
		Query q = em.createQuery(query);
		return (T) q.getSingleResult();
	}

	@Override
	public T findByParameterObject(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public T findByParameterObjectTwo(String parameter, String parameterTwo,
			Object valor, Object valorTwo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get(parameterTwo), valorTwo));
		return em.createQuery(criteria).getSingleResult();
	}

	public boolean removeDetail(String parameter, Object value) {
		System.out.println("Remover " + this.typeT.getSimpleName() + ": "
				+ value);
		String query = "delete from " + this.typeT.getSimpleName()
				+ " pro where  pro." + parameter + ".id=" + value;
		println("Query Servicios: " + query);
		int res = em.createQuery(query).executeUpdate();
		println("resltado : " + res);
		return res == 1;

	}

	@Override
	public T findByParameterObjectThree(String parameter, String parameterTwo,
			String parameterThree, Object valor, Object valorTwo,
			Object valorThree) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get(parameterTwo), valorTwo),
				cb.equal(object.get(parameterThree), valorThree));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public T findByParameterObjectFour(String parameter, String parameterTwo,
			String parameterThree, String parameterFour, Object valor,
			Object valorTwo, Object valorThree, Object valorFour) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get(parameterTwo), valorTwo),
				cb.equal(object.get(parameterThree), valorThree),
				cb.equal(object.get(parameterFour), valorFour));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
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

	@Override
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

	@Override
	public List<T> findAllByParameterObjectThreeQueryOr(String parameter,
			String parameterTwo, String parameterThree, Object valor,
			Object valorTwo, Object valorThree) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(
				cb.or(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo)),
				cb.equal(object.get(parameterThree), valorThree));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllByParameterObjectFourQueryOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			Object valor, Object valorTwo, Object valorThree, Object valorFour) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(
				cb.or(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo)),
				cb.equal(object.get(parameterThree), valorThree),
				cb.equal(object.get(parameterFour), valorFour));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllActiveByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get("estado"), "AC"));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllActiveByParameter(String parameter, Object valor,
			String parameterTwo, Object valorTwo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get(parameterTwo), valorTwo),
				cb.equal(object.get("estado"), "AC"));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findDescAllOrderedByParameter(String parameter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).orderBy(cb.desc(object.get(parameter)));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAscAllOrderedByParameter(String parameter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).orderBy(cb.asc(object.get(parameter)));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllInactiveByParameter(String parameter, Object valor) {
		return null;
	}

	@Override
	public List<T> findAllByEstadoOrderByAsc(String estado) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get("estado"), estado))
				.orderBy(cb.asc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllByEstadoOrderByDesc(String estado) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get("estado"), estado))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public T findByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public BigInteger countTotalRecord() {
		String query = "select count(em) from " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' or em.estado='IN' ";
		println("query:" + query);
		return (BigInteger) em.createNativeQuery(query).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
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
	@Override
	public List<T> findAllActivosByMaxResultOrderByDesc(int maxRows) {
		Query q = em.createQuery("SELECT em FROM " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' order by em.id desc");
		q.setMaxResults(maxRows);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllActivosByMaxResultOrderByAsc(int maxRows) {
		Query q = em.createQuery("SELECT em FROM " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' order by em.id desc");
		q.setMaxResults(maxRows);
		return q.getResultList();
	}

	@Override
	public List<T> findAllByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllActivosByParameter(String parameter, Object valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get("estado"), "AC"));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllActivosByParameterTwo(String parameter,
			String parameterTwo, Object valor, Object valorTwo) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor),
				cb.equal(object.get(parameterTwo), valorTwo),
				cb.equal(object.get("estado"), "AC"));
		return em.createQuery(criteria).getResultList();
	}

	@Override
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

	@Override
	public List<T> findAllByParameterObjectThree(String parameter,
			String parameterTwo, String parameterThree, Object valor,
			Object valorTwo, Object valorThree) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object)
				.where(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo),
						cb.equal(object.get(parameterThree), valorThree))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllByParameterObjectFour(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			Object valor, Object valorTwo, Object valorThree, Object valorFour) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object)
				.where(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo),
						cb.equal(object.get(parameterThree), valorThree),
						cb.equal(object.get(parameterFour), valorFour))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllByParameterObjectFourOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			String valor, String valorTwo, String valorThree, String valorFour) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object)
				.where(cb.or(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo),
						cb.equal(object.get(parameterThree), valorThree),
						cb.equal(object.get(parameterFour), valorFour)))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllByParameterObjectFiveOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			String parameterFive, Object valor, Object valorTwo,
			Object valorThree, Object valorFour, Object valorFive) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object)
				.where(cb.or(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo),
						cb.equal(object.get(parameterThree), valorThree),
						cb.equal(object.get(parameterFour), valorFour),
						cb.equal(object.get(parameterFive), valorFive)))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<T> findAllByParameterObjectFiveOr(String parameter,
			String parameterTwo, String parameterThree, String parameterFour,
			String parameterFive, String valor, String valorTwo,
			String valorThree, String valorFour, String valorFive) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object)
				.where(cb.or(cb.equal(object.get(parameter), valor),
						cb.equal(object.get(parameterTwo), valorTwo),
						cb.equal(object.get(parameterThree), valorThree),
						cb.equal(object.get(parameterFour), valorFour),
						cb.equal(object.get(parameterFive), valorFive)))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
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
	@Override
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
	@Override
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

	@Override
	public List<T> findAllByParameterDate(String parameter, Date valor) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(this.typeT);
		Root<T> object = criteria.from(this.typeT);
		criteria.select(object).where(cb.equal(object.get(parameter), valor))
				.orderBy(cb.desc(object.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
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
				+ paramter + ".id=" + value + " ORDER BY em.id ASC ";
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOneParameter(String state, String stateValue,
			String paramter, Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em." + state + "='" + stateValue + "' and  em."
				+ paramter + ".id=" + value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveThwoParameter(String state, String stateValue,
			String paramter, Object value, String paramter1, Object value1) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em." + state + "='" + stateValue + "' and  em."
				+ paramter + ".id=" + value + "and  em." + paramter1 + ".id="
				+ value1;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveThwoParameter(String state, String stateValue,
			String paramter, Object value, String paramter1, Object value1,
			String fieldOrder, String typeOrder) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em." + state + "='" + stateValue + "' and  em."
				+ paramter + ".id=" + value + " and  em." + paramter1 + ".id="
				+ value1 + " order by em." + fieldOrder + " " + typeOrder;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveParameter(String paramter, Object value,
			String fieldOrder, String order) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where (em.estado='AC' OR em.estado='IN') and  em."
				+ paramter + ".id=" + value + " order by em." + fieldOrder
				+ " " + order;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveParameter(int rows, String paramter,
			Object value, String fieldOrder, String order) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em.estado!='RM' and  em." + paramter + ".id="
				+ value + " order by em." + fieldOrder + " " + order;
		println("Query " + query);
		return em.createQuery(query).setMaxResults(rows).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveParameter(int rows, String paramter,
			Object value, String paramter2, Object value2, String fieldOrder,
			String order) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em.estado!='RM' and  em." + paramter + ".id="
				+ value + "  and  em." + paramter2 + ".id=" + value2
				+ " order by em." + fieldOrder + " " + order;
		println("Query " + query);
		return em.createQuery(query).setMaxResults(rows).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findActiveParameter(String paramter, Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em.estado='AC' and  em." + paramter + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	public Object findActiveMax(String nameField, String tableFist,
			Object value, String tableSecond, Object value1) {
		String query = "select MAX(em." + nameField + ") from "
				+ this.typeT.getSimpleName()
				+ " em where em.estado='AC' and  em." + tableFist + ".id="
				+ value + " and em." + tableSecond + ".id=" + value1;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	public Object findActiveMaxReConsulta(String nameField, String tableFist,
			Object value, String tableSecond, Object value1) {
		String query = "select MAX(em." + nameField + ") from "
				+ this.typeT.getSimpleName()
				+ " em where em.estado='AT' and  em." + tableFist + ".id="
				+ value + " and em." + tableSecond + ".id=" + value1;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	public Object findActiveMax(String nameField, String tableFist, Object value) {
		String query = "select MAX(em." + nameField + ") from "
				+ this.typeT.getSimpleName()
				+ " em where em.estado='AC' and  em." + tableFist + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public Object findCount(String nameField, String parameter,
			String parameterTwo, Object value, Object valueTwo) {
		String query = "select COUNT(em." + nameField + ") from "
				+ this.typeT.getSimpleName()
				+ " em where em.estado='II' and  em." + parameter + ".id="
				+ value + " and em." + parameterTwo + ".id = " + valueTwo;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public Object findCountEvolucionHM(String nameField, String parameter,
			String parameterTwo, Object value, Object valueTwo) {
		String query = "select COUNT(em." + nameField + ") from "
				+ this.typeT.getSimpleName()
				+ " em where em.estado='AC' and  em." + parameter + ".id="
				+ value + " and em." + parameterTwo + ".id = " + valueTwo;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	public Object findCountConsulta(String nameField, String parameter,
			String parameterTwo, Object value, Object valueTwo) {
		String query = "select COUNT(em." + nameField + ") from "
				+ this.typeT.getSimpleName()
				+ " em where em.estado='AT' and  em." + parameter + ".id="
				+ value + " and em." + parameterTwo + ".id = " + valueTwo;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	public Object findCountParaEvolucion(String nameField, String parameter,
			String parameterTwo, String parameterThree, Object value,
			Object valueTwo, Object valueThree) {
		String query = "select COUNT(em." + nameField + ") from "
				+ this.typeT.getSimpleName()
				+ " em where em.estado='AC' and  em." + parameter + ".id="
				+ value + " and em." + parameterTwo + ".id = " + valueTwo
				+ " and em." + parameterThree + ".id = " + valueThree;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	@Override
	public Object findCount(String nameField, String parameter, Object value) {
		String query = "select COUNT(em." + nameField + ") from "
				+ this.typeT.getSimpleName() + " em where em." + parameter
				+ ".id=" + value;
		println("Query " + query);
		return em.createQuery(query).getSingleResult();
	}

	@SuppressWarnings("unused")
	@Override
	public T findMaxTransferencia(String fieldMax, String parameter,
			String parameterTwo, Object value, Object valueTwo) {
		String query2 = "select em from Transferencia em, Historia_Clinica hc, Compania c where em.estado='II' and  em.id_historia_Clinica=hc.id and em.id_compania = c.id ORDER BY em.id DESC LIMIT 1";
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em.estado='II' and  em." + parameter + ".id="
				+ value + " and em." + parameterTwo + ".id = " + valueTwo
				+ " ORDER BY em.id DESC";
		println("Query " + query);
		return executeQuerySingleResult(query);
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
	public List<T> findAllActiveOtherTableAndParameterForNameAutoComplete(
			String nameColumn, String valueColumn, String nameColState,
			String valueState) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ "='"
				+ valueState
				+ "' and  upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn + "%'";
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllActiveOtherTableAndParameterForNameAutoComplete(
			String nameColumn, String valueColumn, String nameColumn1,
			String valueColumn1, String nameColumn2, String valueColumn2,
			String nameColState, String valueState, String tableNameParam,
			Object value) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ "='"
				+ valueState
				+ "' and  upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn
				+ "%' and  upper(translate(em."
				+ nameColumn1
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn1
				+ "%' and  upper(translate(em."
				+ nameColumn2
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn2 + "%'  and  em." + tableNameParam + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOtherTableAndParameterForNamesAutoComplete(
			String nameColumn, String valueColumn, String nameColumn1,
			Object valueColumn1, String nameColumn2, Object valueColumn2,
			String nameColState, String valueState) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ "='"
				+ valueState
				+ "' and ( upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn
				+ "%' or  upper(translate(em."
				+ nameColumn1
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn1
				+ "%' or  upper(translate(em."
				+ nameColumn2
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn2 + "%' );";
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOtherTableAndParameterForNamesAutoComplete(
			String nameColumn, String valueColumn, String nameColumn1,
			String valueColumn1, String nameColumn2, String valueColumn2,
			String nameColState, String valueState, String tableNameParam,
			Object value) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ "='"
				+ valueState
				+ "' and ( upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn
				+ "%' or  upper(translate(em."
				+ nameColumn1
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn1
				+ "%' or  upper(translate(em."
				+ nameColumn2
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn2 + "%' ) and  em." + tableNameParam + ".id="
				+ value;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveOtherTableAndParameterForNamesAutoCompletePacienteCI(
			String nameColumn, String valueColumn, String nameColumn1,
			String valueColumn1, String nameColumn2, String valueColumn2,
			String nameColState, String valueState, String tableNameParam,
			Object value, String nameColumn3, String tableNameParam1,
			String valueColumn3) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ "='"
				+ valueState
				+ "' and ( upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn
				+ "%' or  upper(translate(em."
				+ nameColumn1
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn1
				+ "%' or  upper(translate(em."
				+ nameColumn2
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn2
				+ "%' or uppder(translate(em."
				+ tableNameParam1
				+ "."
				+ nameColumn3
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn3 + "%' ) " + "and  em." + tableNameParam + ".id="
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
	public List<T> findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
			String nameColumn, String valueColumn, String nameColumn1,
			Object valueColumn1, String nameColState, String valueState,
			String tableNameParam) {
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
				+ valueColumn + "%' and em." + nameColumn1 + " = "
				+ valueColumn1;
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
	public T findSingleObject(String tableNameParam, Object value) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where em.estado='AC' and  em." + tableNameParam
				+ ".id=" + value;
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

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveThwoParameter(String state, String stateValue,
			String nameDate, Date date, String paramter, Object value,
			String paramter1, Object value1, String fieldOrder, String typeOrder) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em." + state + "='" + stateValue
				+ " and  to_number(to_char(em." + nameDate
				+ " ,'YYYYMMDD'), '99999999')>="
				+ Time.obtenerFormatoYYYYMMDD(date) + "' and  em." + paramter
				+ ".id=" + value + "and  em." + paramter1 + ".id=" + value1
				+ " order by em." + fieldOrder + " " + typeOrder;
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

	/**
	 * 
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
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value1, String nameTable2Query,
			Object value2, String paramterBegin, Date fechaini,
			String paramterEnd, Date fechafin) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where  em." + nameTableQuery + ".id=" + value1
				+ " and  em." + nameTable2Query + ".id=" + value2
				+ " and to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '99999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '99999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	/**
	 * 
	 * @param nameTableQuery
	 * @param value1
	 * @param nameTable2Query
	 * @param value2
	 * @param paramterBegin
	 * @param fechaini
	 * @param paramterEnd
	 * @param fechafin
	 * @param fieldOrder
	 * @param typeOrder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value1, String nameTable2Query,
			Object value2, String paramterBegin, Date fechaini,
			String paramterEnd, Date fechafin, String fieldOrder,
			String typeOrder) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where  em." + nameTableQuery + ".id=" + value1
				+ " and  em." + nameTable2Query + ".id=" + value2
				+ " and to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '99999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '99999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value1, String nameTable2Query,
			Object value2, String paramterBegin, Date fechaini,
			String paramterEnd, Date fechafin, String fieldOrder,
			String typeOrder, String fieldState, String state) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where em." + fieldState + "=" + state + " and em."
				+ nameTableQuery + ".id=" + value1 + " and  em."
				+ nameTable2Query + ".id=" + value2
				+ " and to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '99999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '99999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	/**
	 * 
	 * @param parameterUsuer
	 * @param usuario
	 * @param nameTableQuery
	 * @param value
	 * @param paramterBegin
	 * @param fechaini
	 * @param paramterEnd
	 * @param fechafin
	 * @return
	 */
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

	/**
	 * 
	 * @param nameTableQuery
	 * @param value
	 * @param paramterBegin
	 * @param fechaini
	 * @param paramterEnd
	 * @param fechafin
	 * @return
	 */
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

	/**
	 * 
	 * @param nameTableQuery
	 * @param value
	 * @param paramterBegin
	 * @param fechaini
	 * @param paramterEnd
	 * @param fechafin
	 * @param fieldOrder
	 * @param typeOrder
	 * @param fieldState
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value, String paramterBegin,
			Date fechaini, String paramterEnd, Date fechafin,
			String fieldOrder, String typeOrder, String fieldState, String state) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em  where em." + fieldState + "=" + state + "  em."
				+ nameTableQuery + ".id=" + value
				+ " and  to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '999999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '999999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin) + "  order by em."
				+ fieldOrder + " " + typeOrder;
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	/**
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllActiveForThwoDatesAndThwoObject(
			String nameTableQuery, Object value, String paramterBegin,
			Date fechaini, String paramterEnd, Date fechafin,
			String fieldState, String state) {
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

	/**
	 * 
	 * @param nameTableQuery
	 * @param value
	 * @param paramterBegin
	 * @param fechaini
	 * @param paramterEnd
	 * @param fechafin
	 * @param paramEstado
	 * @param estadoObject
	 * @return
	 */
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
	public T findByQuerys(String field, String valor, String paramter,
			Object value) {
		String upperQuery = valor.toUpperCase();
		String query = "SELECT em FROM " + this.typeT.getSimpleName()
				+ " em where upper(translate(em." + field
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '"
				+ upperQuery + "' and em.estado='AC'  and em." + paramter
				+ ".id=" + value + " order by em." + field + " asc";
		// println(query);
		Query q = em.createQuery(query);
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
			println("Error en traerGestionesFacturadas: " + e.getMessage());
			return null;
		}
	}

	public EntityManager getEntityManager() {
		return em;
	}

	@SuppressWarnings("unchecked")
	public List<T> findActiveForThwoDateForOtherTable(String parameterUsuer,
			String usuario, String nameTable, Object value,
			String paramterBegin, Date fechaini, String paramterEnd,
			Date fechafin) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where  em." + nameTable + ".id=" + value + " and (em."
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
	public List<T> findActiveForThwoDateForOtherTable(String nameTableMed,
			Object param, String nameTable, Object value, String paramterBegin,
			Date fechaini, String paramterEnd, Date fechafin) {
		String query = "select em from " + this.typeT.getSimpleName()
				+ " em where  em." + nameTable + ".id=" + value + " and em."
				+ nameTableMed + ".id =" + param
				+ " and  to_number(to_char(em." + paramterEnd
				+ "  ,'YYYYMMDD'), '99999999')>="
				+ Time.obtenerFormatoYYYYMMDD(fechaini)
				+ " and  to_number(to_char(em." + paramterEnd
				+ " ,'YYYYMMDD'), '99999999')<="
				+ Time.obtenerFormatoYYYYMMDD(fechafin)
				+ "  order by em.id desc";
		println("Query Factura: " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllAndParameterForNameAutoComplete(String nameColumn,
			String valueColumn, String nameColState, String valueState,
			String nameTable1, Object parameter1, String nameTable,
			Object parameter) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em  where em."
				+ nameColState
				+ " = '"
				+ valueState
				+ "' and upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn + "%' and em." + nameTable1 + ".id = "
				+ parameter1 + " and em." + nameTable + ".id = " + parameter;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllAndParameterForNameAutoCompleteThwoTables(
			String nameColumn, String valueColumn, String nameColState,
			String valueState, String myObjectTable, String tableName,
			String objectTable, Object parameter1, String objectTable1,
			Object parameter) {
		String query = "select em from "
				+ this.typeT.getSimpleName()
				+ " em ,"
				+ tableName
				+ " em1 where em."
				+ nameColState
				+ " = '"
				+ valueState
				+ "' and upper(translate(em."
				+ nameColumn
				+ ", 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ valueColumn + "%'  and em.id =em1." + myObjectTable
				+ ".id and em1." + objectTable + ".id = " + parameter1
				+ " and  em." + objectTable1 + ".id = " + parameter;
		println("Query " + query);
		return em.createQuery(query).getResultList();
	}
}
