/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db_models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miquel Ginés Borràs
 * @mail ginesborrasm@gmail.com
 */
@Entity
@Table(name = "mgines_logins")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logins.findAll", query = "SELECT l FROM Logins l"),
    @NamedQuery(name = "Logins.findById", query = "SELECT l FROM Logins l WHERE l.id = :id"),
    @NamedQuery(name = "Logins.findByLoginTime", query = "SELECT l FROM Logins l WHERE l.loginTime = :loginTime")})
public class Logins implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;
    @JoinColumn(name = "usr_id", referencedColumnName = "id")
    @ManyToOne
    private Users usrId;

    public Logins() {
    }

    public Logins(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Users getUsrId() {
        return usrId;
    }

    public void setUsrId(Users usrId) {
        this.usrId = usrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logins)) {
            return false;
        }
        Logins other = (Logins) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db_models.Logins[ id=" + id + " ]";
    }

}
