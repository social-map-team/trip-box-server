package com.socialmap.server.gui;

import org.eclipse.jetty.server.Server;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by yy on 2/28/15.
 */
public class ConsoleFrame extends JFrame implements ApplicationContextAware {
    JButton _start = new JButton("启动");
    JButton _stop = new JButton("停止");
    JButton _db = new JButton("查看数据库");
    JTextArea _console = new JTextArea(25, 80);
    private Server server;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("console.......");
    }

    public ConsoleFrame(final Server server) throws HeadlessException {
        this.server = server;

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        container.setLayout(new BorderLayout());


        JPanel control = new JPanel();
        control.add(_start);
        control.add(_stop);
        control.add(_db);
        container.add(control, BorderLayout.NORTH);

        _stop.setEnabled(false);
        _db.setEnabled(false);

        _db.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
                DatabaseFrame databaseFrame = new DatabaseFrame();
                databaseFrame.setVisible(true);
            }
        });

        _start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server.start();
                    if (_console.getText().isEmpty()){
                        _console.setText("服务器已启动");
                    }else {
                        _console.setText(_console.getText() + "\n服务器已启动");
                    }
                    _start.setEnabled(false);
                    _stop.setEnabled(true);
                    _db.setEnabled(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
        });

        _stop.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server.stop();
                    _console.setText(_console.getText() + "\n服务器已关闭");
                    _start.setEnabled(true);
                    _stop.setEnabled(false);
                    _db.setEnabled(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
        });

        container.add(_console, BorderLayout.CENTER);

        add(container);

        setTitle("旅游盒子 - 服务器");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
    }
}
