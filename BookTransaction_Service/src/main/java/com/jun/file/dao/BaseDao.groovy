package com.jun.file.dao;

import com.jun.file.util.HibernateUtil
import groovy.transform.CompileStatic;

import org.hibernate.Query;
import org.hibernate.Session;


import java.util.Map
@CompileStatic
abstract class BaseDao{

	private def doWithSession(Closure func){
		Session session = null; // Session����
		def result = null;
		try {
			// ��ȡSession
			session = HibernateUtil.getSession();
			session.beginTransaction(); // ��������
			result = func(session);
			session.getTransaction().commit(); // �ύ����
		} catch (Exception e) {
			e.printStackTrace(); // ��ӡ�쳣��Ϣ
			session.getTransaction().rollback();// �ع�����
		} finally {
			HibernateUtil.closeSession();
		}
		return result;
	}

	def save(def obj) {
		return doWithSession({ Session session->
			session.save(obj)
		});
	}

	abstract Class getT();

	public <T> List<T> findList(Map<String,Object> where){
		(List) doWithSession({ Session session->
			def query = createQuery("from",session, where,null,null)
			return query.list();
		});
	}
	
	public <T> List<T> findList(Map<String,Object> where,int offset,int limit){
		(List) doWithSession({ Session session->
			def query = createQuery("from",session, where,offset,limit)
			return query.list();
		});
	}

	public <T> T findOne(Map<String,Object> where){
		List list =findList(where?:new HashMap<String,Object>());
		if(list?.size()==0){
			return null;
		}
		return list.get(0);
	}

	int delete(Object id){
		deleteWhere(["id":id]);
	}

	int deleteAll(){
		(Integer)doWithSession {Session session->
			def hql = "delete from "+getT().getSimpleName();
			def query= session.createQuery(hql)
			return query.executeUpdate();
		}
	}

	int deleteWhere(Map<String,Object> where){
		(Integer)doWithSession {Session session->
			def query=createQuery("delete from", session, where,null,null)
			query.executeUpdate();
		}
	}

	private Query createQuery(String prefix,Session session,Map<String,Object> where,Integer offset,Integer limit){
		def setQueryLimit = {
			Query q->
			if (offset!=null && limit!=null) {
				q.setFirstResult(offset);
				q.setMaxResults(limit);
			}
		}
		def hql = prefix+" "+getT().getSimpleName();
		def query;
		if (!where||where.size()==0) {
			query= session.createQuery(hql)
			setQueryLimit(query);
			return query
		}
		hql+=" where "
		def i =0
		where?.each  {  key,value->
			i++
			if(i!=where.size()){
				hql+=key+"=? and "
			}else{
				hql+=key+"=?"
			}
		};
		query= session.createQuery(hql)
		setQueryLimit(query);
		i=0
		where?.each {  k,v->
			query.setParameter(i, v)
			i++
		};
		return query;
	}

	/**
	 * 
	 * @param where where����
	 * @param fields ���µ��ֶ�
	 * @param object ����Ķ���
	 * @return �Ƿ��ҵ���
	 */
	boolean upsert(Map<String,Object> where,Map<String,Object> fields,Object object){
		List list = findList(where);
		if(list){
			update(where, fields);
			return true;
		}
		save(object)
		return false;
	}

	void upsert(Object obj){
		doWithSession { Session session->
			session.saveOrUpdate(obj)
		}
	}

	void update(Map<String,Object> where,Map<String,Object> fields){
		doWithSession {Session session->
			def hql = "update "+getT().getSimpleName()+" set ";
			def query
			def i =0
			fields?.each { k,v->
				i++
				if (i==fields.size()) {
					hql+=k+"=?";
				}else{
					hql+=k+"=?,";
				}
			}
			if(!where){
				query = session.createQuery(hql);
				i=0;
				fields?.each { k,v->
					query.setParameter(i, v)
					i++
				}
				query.executeUpdate();
				return
			}
			hql+=" where "
			i=0
			where?.each  {  key,value->
				i++
				if(i!=where.size()){
					hql+=key+"=? and "
				}else{
					hql+=key+"=?"
				}
			};
			query = session.createQuery(hql);
			i=0;
			fields?.each { k,v->
				query.setParameter(i, v)
				i++
			}
			where?.each { k,v->
				query.setParameter(i, v)
				i++
			}
			query.executeUpdate();
		}
	}
}
