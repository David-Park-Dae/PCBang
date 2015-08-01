package Home;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FrameLogin extends JFrame implements ActionListener
{
	Dimension d;
	//background �г� �ϳ� �ڰ� ������ ���� 3�� �θ鼭 �г� 3���� ����
	//line1 : �ΰ�
	//line2 : ���̵� 
	//line3 : pwd
	//line4 : ��ư
	JPanel plBackground;
	JPanel plLine1;
	JPanel plLine2;
	JPanel plLine3;
	JPanel plLine4;
	
	JLabel lbId;
	JLabel lbPwd;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton btnLogin;
	JButton btnExit;
	
	JOptionPane jop;
	
	FrameLogin()
	{
		setTitle("������ �α���");
		
		jop = new JOptionPane();
		Toolkit tool = Toolkit.getDefaultToolkit();
		d = tool.getScreenSize();
		int frameX = 400;
		int frameY = 200;
		setBounds((d.width/2)-(frameX/2), (d.height/2)-(frameY/2), frameX, frameY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//�гε� �ʱ�ȭ
		plBackground = new JPanel();
		plBackground.setLayout(new GridLayout(4,1, 0, -5));
		plLine1 = new JPanel();
		plLine2 = new JPanel();
		plLine3 = new JPanel();
		plLine4 = new JPanel();
		plBackground.add(plLine1);
		plBackground.add(plLine2);
		plBackground.add(plLine3);
		plBackground.add(plLine4);
		
		//��, ��ư, �ؽ�Ʈ �ʵ� �ʱ�ȭ
		lbId = new JLabel("������ ���� : ");
		lbPwd = new JLabel("�� �� �� ȣ : ");
		tfId = new JTextField(10);
		pfPwd = new JPasswordField(10);
		btnLogin = new JButton("Login");
		btnExit = new JButton("Exit");
		
		//�׼Ǹ����� �߰�
		btnLogin.addActionListener(this);
		btnExit.addActionListener(this);
		
		//��ư, �ؽ�Ʈ �ʵ� �ֱ�
		plLine2.add(lbId);
		plLine2.add(tfId);
		
		plLine3.add(lbPwd);
		plLine3.add(pfPwd);
		
		plLine4.add(btnLogin);
		plLine4.add(btnExit);
		
		
		
		add(plBackground);
		setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object obj = new Object();
		obj = e.getSource();
		
		if(obj.equals(btnLogin))
		{
			FrameServer fs = new FrameServer();
			dispose();
		}
		if(obj.equals(btnExit))
		{
			int result = jop.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "�˸�", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
			
		}
	}
	
	public static void main(String[] args)
	{
		new FrameLogin();
	}
}
