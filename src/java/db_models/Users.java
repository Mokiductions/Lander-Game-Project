/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db_models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Miquel Ginés Borràs
 * @mail ginesborrasm@gmail.com
 */
@Entity
@Table(name = "mgines_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByUsr", query = "SELECT u FROM Users u WHERE u.usr = :usr"),
    @NamedQuery(name = "Users.findByPwd", query = "SELECT u FROM Users u WHERE u.pwd = :pwd")})
public class Users implements Serializable {

    @OneToMany(mappedBy = "usrId")
    private List<Games> gamesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "usr")
    private String usr;
    @Basic(optional = false)
    @Column(name = "pwd")
    private String pwd;
    @OneToMany(mappedBy = "usrId")
    private List<Logins> loginsList;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String usr, String pwd) {
        this.id = id;
        this.usr = usr;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @XmlTransient
    public List<Logins> getLoginsList() {
        return loginsList;
    }

    public void setLoginsList(List<Logins> loginsList) {
        this.loginsList = loginsList;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db_models.Users[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Games> getGamesList() {
        return gamesList;
    }

    public void setGamesList(List<Games> gamesList) {
        this.gamesList = gamesList;
    }

}
