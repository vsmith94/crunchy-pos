/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Vinicius Smith
 */
@Entity
@Table(name = "transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
    , @NamedQuery(name = "Transaction.findByTransactionID", query = "SELECT t FROM Transaction t WHERE t.transactionID = :transactionID")
    , @NamedQuery(name = "Transaction.getHighestIndex", query = "SELECT MAX(t.transactionID) FROM Transaction t")
    , @NamedQuery(name = "Transaction.findByTransactionDate", query = "SELECT t FROM Transaction t WHERE t.transactionDate = :transactionDate")
    , @NamedQuery(name = "Transaction.findByFinalPrice", query = "SELECT t FROM Transaction t WHERE t.finalPrice = :finalPrice")
    , @NamedQuery(name = "Transaction.findByTransactionBetween", query = "SELECT t FROM Transaction t WHERE t.transactionDate between CAST(:fromDate AS DATE) and CAST(:toDate AS DATETIME)")
    , @NamedQuery(name = "Transaction.findDailyTotal", query = "SELECT SUM(t.finalPrice) FROM Transaction t WHERE t.transactionDate = :date")})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "transactionID")
    private Integer transactionID;
    @Basic(optional = false)
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Basic(optional = false)
    @Column(name = "final_price")
    private float finalPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction", fetch = FetchType.EAGER)
    private List<TransactionItem> transactionItemList;

    public Transaction() {
    }

    public Transaction(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Transaction(Integer transactionID, Date transactionDate, float finalPrice) {
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.finalPrice = finalPrice;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    @XmlTransient
    public List<TransactionItem> getTransactionItemList() {
        return transactionItemList;
    }

    public void setTransactionItemList(List<TransactionItem> transactionItemList) {
        this.transactionItemList = transactionItemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionID != null ? transactionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.transactionID == null && other.transactionID != null) || (this.transactionID != null && !this.transactionID.equals(other.transactionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Transaction[ transactionID=" + transactionID + " ]";
    }

}
