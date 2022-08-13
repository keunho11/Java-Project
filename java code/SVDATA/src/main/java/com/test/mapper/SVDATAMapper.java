package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.test.entity.Data;

@Mapper
public interface SVDATAMapper {
	@Select("select status, temp from tbl_svdata")
	public List<Data> selectDatasList();
	
	@Insert("insert into tbl_svdata (status, temp) values (\'${status}\', \'${temp}\')")
	public void insertData(@Param("status") String status, @Param("temp") int temp);
	
	
	@Update("update tbl_svdata set status = \'${status}\'")
	public void updataData(@Param("status") String status);
	
	@Update("update tbl_svdata set temp = temp+1")
	public void pTemp();
	
	@Update("update tbl_svdata set temp = temp-1")
	public void mTemp();
	
}
