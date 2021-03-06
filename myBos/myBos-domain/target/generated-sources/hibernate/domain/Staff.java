package domain;
// Generated 2017-8-14 21:54:52 by Hibernate Tools 3.2.2.GA


import domain.qp.Order;
import domain.qp.WorkBill;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 * Staff generated by hbm2java
 */
@Entity
@Table(name="bc_staff"
    ,catalog="mybos"
    , uniqueConstraints = @UniqueConstraint(columnNames="TELEPHONE") 
)
public class Staff  implements java.io.Serializable {


     private String id;
     private String name;
     private String telephone;
     private Integer haspda;
     private Integer deltag;
     private String station;
     private String standard;
     private Set<Order> orders = new HashSet<Order>(0);
     private Set<DecidedZone> decidedZones = new HashSet<DecidedZone>(0);
     private Set<WorkBill> workBills = new HashSet<WorkBill>(0);

    public Staff() {
    }

	
    public Staff(String telephone) {
        this.telephone = telephone;
    }
    public Staff(String name, String telephone, Integer haspda, Integer deltag, String station, String standard, Set<Order> orders, Set<DecidedZone> decidedZones, Set<WorkBill> workBills) {
       this.name = name;
       this.telephone = telephone;
       this.haspda = haspda;
       this.deltag = deltag;
       this.station = station;
       this.standard = standard;
       this.orders = orders;
       this.decidedZones = decidedZones;
       this.workBills = workBills;
    }
   
     @GenericGenerator(name="generator", strategy="uuid")@Id @GeneratedValue(generator="generator")
    
    @Column(name="ID", unique=true, nullable=false, length=32)
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name="NAME", length=20)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="TELEPHONE", unique=true, nullable=false, length=20)
    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Column(name="HASPDA")
    public Integer getHaspda() {
        return this.haspda;
    }
    
    public void setHaspda(Integer haspda) {
        this.haspda = haspda;
    }
    
    @Column(name="DELTAG")
    public Integer getDeltag() {
        return this.deltag;
    }
    
    public void setDeltag(Integer deltag) {
        this.deltag = deltag;
    }
    
    @Column(name="STATION", length=40)
    public String getStation() {
        return this.station;
    }
    
    public void setStation(String station) {
        this.station = station;
    }
    
    @Column(name="STANDARD", length=100)
    public String getStandard() {
        return this.standard;
    }
    
    public void setStandard(String standard) {
        this.standard = standard;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="staff")
    public Set<Order> getOrders() {
        return this.orders;
    }
    
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="staff")
    public Set<DecidedZone> getDecidedZones() {
        return this.decidedZones;
    }
    
    public void setDecidedZones(Set<DecidedZone> decidedZones) {
        this.decidedZones = decidedZones;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="staff")
    public Set<WorkBill> getWorkBills() {
        return this.workBills;
    }
    
    public void setWorkBills(Set<WorkBill> workBills) {
        this.workBills = workBills;
    }




}


