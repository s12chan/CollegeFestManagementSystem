package com.example.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EventInformation;

@Repository
public interface EventInformationRepository extends JpaRepository<EventInformation, Long> 
{
List<EventInformation> findByEmail(String email);
List<EventInformation> findByFesttypeAndStatusAndStartdateAfter(String festtype,String status,Date startdate);
List<EventInformation> findByStartdateBetweenAndStatusAndStartdateAfter(Date startdate,Date enddate,String status,Date startdate1);
List<EventInformation> findByAddressStartingWithAndStatusAndStartdateAfter(String address,String status,Date startdate);
List<EventInformation> findByStatus(String status);
List<EventInformation> findByStatusAndStartdateAfter(String status,Date startdate);
Optional<EventInformation> findByEnddate(Date enddate);
Boolean existsByFesttypeAndEventname(String festtype,String eventname);
}
