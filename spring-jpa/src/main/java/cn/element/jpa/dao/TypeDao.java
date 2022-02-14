package cn.element.jpa.dao;

import cn.element.jpa.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TypeDao extends JpaRepository<Type, Integer>, JpaSpecificationExecutor<Type> {

}
