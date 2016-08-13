package ch.reserveyourroom.address.dao.impl;


import ch.reserveyourroom.address.dao.AddressDao;
import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.common.dao.impl.GenericDaoImpl;

import javax.ejb.Stateless;

@Stateless
public class AddressDaoImpl extends GenericDaoImpl<Address> implements AddressDao {
}
