/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Wed Nov 23 16:38:23 CST 2005 by MyEclipse Hibernate Tool.
 */
package com.gzunicorn.hibernate.viewmanager;

import java.io.Serializable;

/**
 * A class that represents a row in the ViewRoleAllowFlagY table. 
 * You can customize the behavior of this class by editing the class, {@link Viewroleallowflagy()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized * by MyEclipse Hibernate tool integration.
 */
public abstract class AbstractViewroleallowflagy 
    implements Serializable
{

    /** The value of the simple roleid property. */
    private java.lang.String roleid;

    /** The value of the simple rolename property. */
    private java.lang.String rolename;

    /**
     * Simple constructor of AbstractViewroleallowflagy instances.
     */
    public AbstractViewroleallowflagy()
    {
    }

    /**
     * Return the value of the RoleID column.
     * @return java.lang.String
     */
    public java.lang.String getRoleid()
    {
        return this.roleid;
    }

    /**
     * Set the value of the RoleID column.
     * @param roleid
     */
    public void setRoleid(java.lang.String roleid)
    {
        this.roleid = roleid;
    }

    /**
     * Return the value of the RoleName column.
     * @return java.lang.String
     */
    public java.lang.String getRolename()
    {
        return this.rolename;
    }

    /**
     * Set the value of the RoleName column.
     * @param rolename
     */
    public void setRolename(java.lang.String rolename)
    {
        this.rolename = rolename;
    }
}
