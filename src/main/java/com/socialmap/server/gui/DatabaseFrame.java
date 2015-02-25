package com.socialmap.server.gui;

import com.socialmap.server.App;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.Component;
import java.util.*;

/**
 * Created by yy on 2/28/15.
 */
public class DatabaseFrame extends JFrame {

    JSplitPane splitPane;

    JPanel left;
    JPanel right;

    public Component userList(){
        Object[] titles = {
                "状态",
                "头像",
                "用户名",
                "密码",
                "账户锁定",
                "真实姓名",
                "身份证号",
                "性别",
                "电话",
                "电子邮箱"
        };
        Session session = App.sessionFactoryBean.getObject().openSession();
        Query q = session.createQuery("select status, avatar.id, username, password, enabled, realname, idcard, gender, phone, email from users");
        java.util.List<Object[]> result = q.list();
        Object[][] data = new Object[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            data[i] = result.get(i);
        }
        return new JTable(data, titles);
    }

    public DatabaseFrame() throws HeadlessException {
        left = new JPanel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Entity（实体）");
        DefaultMutableTreeNode user = new DefaultMutableTreeNode("User（用户）");
        DefaultMutableTreeNode team = new DefaultMutableTreeNode("Team（团队）");
        DefaultMutableTreeNode image = new DefaultMutableTreeNode("Image（图片）");
        DefaultMutableTreeNode authority = new DefaultMutableTreeNode("Authority（角色）");
        root.add(user);
        root.add(team);
        root.add(image);
        root.add(authority);
        JTree tree = new JTree(root);
        left.add(new JScrollPane(tree));

        right = new JPanel();
        right.add(new JScrollPane(userList()));

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        setSize(600, 500);
        setLocationRelativeTo(null);
        add(splitPane);
    }
}
