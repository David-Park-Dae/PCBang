package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

public class FrameServer extends JFrame
{
	Dimension d1;// ������� ũ�⸦ ���ϱ� ���� �ν��Ͻ�
	JOptionPane jop; // �˸�ǥ�ø� ���� jop

	JPanel plBackground; // ������ ���� �ٷ� �� �ǳ�
	JScrollPane mainScrollPane; // ���� ��ũ���� ���� ��ũ�� ��
	JPanel mainScreen; // �Ŵ��� �Ʒ� ��ũ��, ��ũ�� �ǿ� ����.
	JMenuBar bar; // �Ŵ���
	JMenu menuBasic; // �⺻�����Ŵ�
	JMenu menuMember; // ȸ�������Ŵ�
	JMenu menuFood; // ���İ����Ŵ�
	JMenuItem itemBasicMoney; // �⺻���� ������ ���������
	JMenuItem itemBasicExit; // �⺻���� ������ ����

	// mainscreen gridLayout�� �� ���ε�
	JPanel[] lines;
	JPanel plLine1;
	JPanel plLine2;
	JPanel plLine3;
	JPanel plLine4;
	JPanel plLine5;

	// ��ǻ�� ������ �̹����� �迭
	ImageIcon[] imgLaptopR;
	ImageIcon imgLaptop01r;
	ImageIcon imgLaptop02r;
	ImageIcon imgLaptop03r;
	ImageIcon imgLaptop04r;
	ImageIcon imgLaptop05r;
	ImageIcon imgLaptop06r;
	ImageIcon imgLaptop07r;
	ImageIcon imgLaptop08r;
	ImageIcon imgLaptop09r;
	ImageIcon imgLaptop10r;
	ImageIcon imgLaptop11r;
	ImageIcon imgLaptop12r;
	ImageIcon imgLaptop13r;
	ImageIcon imgLaptop14r;
	ImageIcon imgLaptop15r;
	ImageIcon imgLaptop16r;
	ImageIcon imgLaptop17r;
	ImageIcon imgLaptop18r;
	ImageIcon imgLaptop19r;
	ImageIcon imgLaptop20r;
	ImageIcon imgLaptop21r;
	ImageIcon imgLaptop22r;
	ImageIcon imgLaptop23r;
	ImageIcon imgLaptop24r;
	ImageIcon imgLaptop25r;

	ImageIcon[] imgLaptopS;
	ImageIcon imgLaptop01s;
	ImageIcon imgLaptop02s;
	ImageIcon imgLaptop03s;
	ImageIcon imgLaptop04s;
	ImageIcon imgLaptop05s;
	ImageIcon imgLaptop06s;
	ImageIcon imgLaptop07s;
	ImageIcon imgLaptop08s;
	ImageIcon imgLaptop09s;
	ImageIcon imgLaptop10s;
	ImageIcon imgLaptop11s;
	ImageIcon imgLaptop12s;
	ImageIcon imgLaptop13s;
	ImageIcon imgLaptop14s;
	ImageIcon imgLaptop15s;
	ImageIcon imgLaptop16s;
	ImageIcon imgLaptop17s;
	ImageIcon imgLaptop18s;
	ImageIcon imgLaptop19s;
	ImageIcon imgLaptop20s;
	ImageIcon imgLaptop21s;
	ImageIcon imgLaptop22s;
	ImageIcon imgLaptop23s;
	ImageIcon imgLaptop24s;
	ImageIcon imgLaptop25s;

	ImageIcon imgBlank; //������ ǥ���� �׳� �Ͼ� �� �̹���
	
	// ��ǻ�� �󺧰� �迭
	JLabel lbBlank1;
	JLabel lbBlank2;
	JLabel lbBlank3;
	JLabel lbBlank4;
	JLabel lbBlank5;
	
	JLabel[] lbLaptop;
	JLabel lbLaptop01;
	JLabel lbLaptop02;
	JLabel lbLaptop03;
	JLabel lbLaptop04;
	JLabel lbLaptop05;
	JLabel lbLaptop06;
	JLabel lbLaptop07;
	JLabel lbLaptop08;
	JLabel lbLaptop09;
	JLabel lbLaptop10;
	JLabel lbLaptop11;
	JLabel lbLaptop12;
	JLabel lbLaptop13;
	JLabel lbLaptop14;
	JLabel lbLaptop15;
	JLabel lbLaptop16;
	JLabel lbLaptop17;
	JLabel lbLaptop18;
	JLabel lbLaptop19;
	JLabel lbLaptop20;
	JLabel lbLaptop21;
	JLabel lbLaptop22;
	JLabel lbLaptop23;
	JLabel lbLaptop24;
	JLabel lbLaptop25;

	// ��Ŭ���� �˾� �̹���
	JPopupMenu jpm;
	JMenuItem popItemName;
	JMenuItem popItemStart;
	JMenuItem popItemPay;
	JMenuItem popItemCharge;
	JMenuItem popItemChat;

	// Pc������ �˾��Ŵ� �׼� �����ʿ��� ��ȣ�� ������ ��
	int currentPcNumber = -1;
	
	int pcTotal = 25; //��ǻ���� �� ����
	int pcAvailable = 0; //���� ��밡���� PC����

	public FrameServer()
	{
		setTitle("������ ���α׷�");
		Toolkit tool = Toolkit.getDefaultToolkit();
		d1 = tool.getScreenSize();// ����� ������ ����
		// width�� ȭ�� ũ���, height�� �۾�ǥ������ ����� 50�� �߶���
		setBounds(0, 0, (int) d1.getWidth(), (int) d1.getHeight() - 50);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// x������ �����Ű�� ����.
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				int result = jop.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "�˸�",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});

		// �ڵ鷯�� ����
		MouseHandler mouseHandler = new MouseHandler();
		ActionHandler actionHandler = new ActionHandler();

		// �ʱ�ȭ
		jop = new JOptionPane();
		lines = new JPanel[5];
		plLine1 = new JPanel();
		plLine2 = new JPanel();
		plLine3 = new JPanel();
		plLine4 = new JPanel();
		plLine5 = new JPanel();
		lines[0] = plLine1;
		lines[1] = plLine2;
		lines[2] = plLine3;
		lines[3] = plLine4;
		lines[4] = plLine5;

		plBackground = new JPanel();
		mainScreen = new JPanel();
		mainScrollPane = new JScrollPane(mainScreen,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// ��ܸŴ���
		bar = new JMenuBar();
		menuBasic = new JMenu("�⺻����");
		menuMember = new JMenu("ȸ������");
		menuFood = new JMenu("���İ���");
		itemBasicMoney = new JMenuItem("���������");
		itemBasicExit = new JMenuItem("���α׷� ����");

		itemBasicExit.addActionListener(actionHandler);

		// add�κ�
		// plBackground add
		add(plBackground);
		plBackground.setLayout(new BorderLayout());
		plBackground.add(bar, "North");
		plBackground.add(mainScrollPane, "Center");

		// �Ŵ��� add
		bar.add(menuBasic);
		bar.add(menuMember);
		bar.add(menuFood);
		menuBasic.add(itemBasicMoney);
		menuBasic.add(itemBasicExit);

		bar.addMouseListener(mouseHandler);
		menuBasic.addMouseListener(mouseHandler);
		menuFood.addMouseListener(mouseHandler);
		menuMember.addMouseListener(mouseHandler);

		// �� ���� ��� ���ν�ũ�� ����
		mainScreen.setLayout(new GridLayout(5, 1));

		// ���ν�ũ�� �׸��� ���̾ƿ� �� ���ٴ� �� �гε�
		for (JPanel j : lines)
		{
			j.setBackground(Color.WHITE);
			j.setLayout(new FlowLayout(FlowLayout.LEFT));
			j.addMouseListener(mouseHandler);
		}
		mainScreen.add(plLine1);
		mainScreen.add(plLine2);
		mainScreen.add(plLine3);
		mainScreen.add(plLine4);
		mainScreen.add(plLine5);

		// PC�󺧰� �����ܳ�� �ʱ�ȭ�ϰ� �迭�� �ֱ�
		imgBlank = new ImageIcon("src/images/laptops/blank.jpg");
		
		imgLaptopR = new ImageIcon[pcTotal];
		imgLaptop01r = new ImageIcon("src/images/laptops/laptop01r.jpg");
		imgLaptop02r = new ImageIcon("src/images/laptops/laptop02r.jpg");
		imgLaptop03r = new ImageIcon("src/images/laptops/laptop03r.jpg");
		imgLaptop04r = new ImageIcon("src/images/laptops/laptop04r.jpg");
		imgLaptop05r = new ImageIcon("src/images/laptops/laptop05r.jpg");
		imgLaptop06r = new ImageIcon("src/images/laptops/laptop06r.jpg");
		imgLaptop07r = new ImageIcon("src/images/laptops/laptop07r.jpg");
		imgLaptop08r = new ImageIcon("src/images/laptops/laptop08r.jpg");
		imgLaptop09r = new ImageIcon("src/images/laptops/laptop09r.jpg");
		imgLaptop10r = new ImageIcon("src/images/laptops/laptop10r.jpg");
		imgLaptop11r = new ImageIcon("src/images/laptops/laptop11r.jpg");
		imgLaptop12r = new ImageIcon("src/images/laptops/laptop12r.jpg");
		imgLaptop13r = new ImageIcon("src/images/laptops/laptop13r.jpg");
		imgLaptop14r = new ImageIcon("src/images/laptops/laptop14r.jpg");
		imgLaptop15r = new ImageIcon("src/images/laptops/laptop15r.jpg");
		imgLaptop16r = new ImageIcon("src/images/laptops/laptop16r.jpg");
		imgLaptop17r = new ImageIcon("src/images/laptops/laptop17r.jpg");
		imgLaptop18r = new ImageIcon("src/images/laptops/laptop18r.jpg");
		imgLaptop19r = new ImageIcon("src/images/laptops/laptop19r.jpg");
		imgLaptop20r = new ImageIcon("src/images/laptops/laptop20r.jpg");
		imgLaptop21r = new ImageIcon("src/images/laptops/laptop21r.jpg");
		imgLaptop22r = new ImageIcon("src/images/laptops/laptop22r.jpg");
		imgLaptop23r = new ImageIcon("src/images/laptops/laptop23r.jpg");
		imgLaptop24r = new ImageIcon("src/images/laptops/laptop24r.jpg");
		imgLaptop25r = new ImageIcon("src/images/laptops/laptop25r.jpg");
		imgLaptopR[0] = imgLaptop01r;
		imgLaptopR[1] = imgLaptop02r;
		imgLaptopR[2] = imgLaptop03r;
		imgLaptopR[3] = imgLaptop04r;
		imgLaptopR[4] = imgLaptop05r;
		imgLaptopR[5] = imgLaptop06r;
		imgLaptopR[6] = imgLaptop07r;
		imgLaptopR[7] = imgLaptop08r;
		imgLaptopR[8] = imgLaptop09r;
		imgLaptopR[9] = imgLaptop10r;
		imgLaptopR[10] = imgLaptop11r;
		imgLaptopR[11] = imgLaptop12r;
		imgLaptopR[12] = imgLaptop13r;
		imgLaptopR[13] = imgLaptop14r;
		imgLaptopR[14] = imgLaptop15r;
		imgLaptopR[15] = imgLaptop16r;
		imgLaptopR[16] = imgLaptop17r;
		imgLaptopR[17] = imgLaptop18r;
		imgLaptopR[18] = imgLaptop19r;
		imgLaptopR[19] = imgLaptop20r;
		imgLaptopR[20] = imgLaptop21r;
		imgLaptopR[21] = imgLaptop22r;
		imgLaptopR[22] = imgLaptop23r;
		imgLaptopR[23] = imgLaptop24r;
		imgLaptopR[24] = imgLaptop25r;

		imgLaptopS = new ImageIcon[pcTotal];
		imgLaptop01s = new ImageIcon("src/images/laptops/laptop01s.jpg");
		imgLaptop02s = new ImageIcon("src/images/laptops/laptop02s.jpg");
		imgLaptop03s = new ImageIcon("src/images/laptops/laptop03s.jpg");
		imgLaptop04s = new ImageIcon("src/images/laptops/laptop04s.jpg");
		imgLaptop05s = new ImageIcon("src/images/laptops/laptop05s.jpg");
		imgLaptop06s = new ImageIcon("src/images/laptops/laptop06s.jpg");
		imgLaptop07s = new ImageIcon("src/images/laptops/laptop07s.jpg");
		imgLaptop08s = new ImageIcon("src/images/laptops/laptop08s.jpg");
		imgLaptop09s = new ImageIcon("src/images/laptops/laptop09s.jpg");
		imgLaptop10s = new ImageIcon("src/images/laptops/laptop10s.jpg");
		imgLaptop11s = new ImageIcon("src/images/laptops/laptop11s.jpg");
		imgLaptop12s = new ImageIcon("src/images/laptops/laptop12s.jpg");
		imgLaptop13s = new ImageIcon("src/images/laptops/laptop13s.jpg");
		imgLaptop14s = new ImageIcon("src/images/laptops/laptop14s.jpg");
		imgLaptop15s = new ImageIcon("src/images/laptops/laptop15s.jpg");
		imgLaptop16s = new ImageIcon("src/images/laptops/laptop16s.jpg");
		imgLaptop17s = new ImageIcon("src/images/laptops/laptop17s.jpg");
		imgLaptop18s = new ImageIcon("src/images/laptops/laptop18s.jpg");
		imgLaptop19s = new ImageIcon("src/images/laptops/laptop19s.jpg");
		imgLaptop20s = new ImageIcon("src/images/laptops/laptop20s.jpg");
		imgLaptop21s = new ImageIcon("src/images/laptops/laptop21s.jpg");
		imgLaptop22s = new ImageIcon("src/images/laptops/laptop22s.jpg");
		imgLaptop23s = new ImageIcon("src/images/laptops/laptop23s.jpg");
		imgLaptop24s = new ImageIcon("src/images/laptops/laptop24s.jpg");
		imgLaptop25s = new ImageIcon("src/images/laptops/laptop25s.jpg");
		imgLaptopS[0] = imgLaptop01s;
		imgLaptopS[1] = imgLaptop02s;
		imgLaptopS[2] = imgLaptop03s;
		imgLaptopS[3] = imgLaptop04s;
		imgLaptopS[4] = imgLaptop05s;
		imgLaptopS[5] = imgLaptop06s;
		imgLaptopS[6] = imgLaptop07s;
		imgLaptopS[7] = imgLaptop08s;
		imgLaptopS[8] = imgLaptop09s;
		imgLaptopS[9] = imgLaptop10s;
		imgLaptopS[10] = imgLaptop11s;
		imgLaptopS[11] = imgLaptop12s;
		imgLaptopS[12] = imgLaptop13s;
		imgLaptopS[13] = imgLaptop14s;
		imgLaptopS[14] = imgLaptop15s;
		imgLaptopS[15] = imgLaptop16s;
		imgLaptopS[16] = imgLaptop17s;
		imgLaptopS[17] = imgLaptop18s;
		imgLaptopS[18] = imgLaptop19s;
		imgLaptopS[19] = imgLaptop20s;
		imgLaptopS[20] = imgLaptop21s;
		imgLaptopS[21] = imgLaptop22s;
		imgLaptopS[22] = imgLaptop23s;
		imgLaptopS[23] = imgLaptop24s;
		imgLaptopS[24] = imgLaptop25s;
		
		lbBlank1 = new JLabel();
		lbBlank2 = new JLabel();
		lbBlank3 = new JLabel();
		lbBlank4 = new JLabel();
		lbBlank5 = new JLabel();
		
		lbLaptop = new JLabel[pcTotal];
		lbLaptop01 = new JLabel();
		lbLaptop02 = new JLabel();
		lbLaptop03 = new JLabel();
		lbLaptop04 = new JLabel();
		lbLaptop05 = new JLabel();
		lbLaptop06 = new JLabel();
		lbLaptop07 = new JLabel();
		lbLaptop08 = new JLabel();
		lbLaptop09 = new JLabel();
		lbLaptop10 = new JLabel();
		lbLaptop11 = new JLabel();
		lbLaptop12 = new JLabel();
		lbLaptop13 = new JLabel();
		lbLaptop14 = new JLabel();
		lbLaptop15 = new JLabel();
		lbLaptop16 = new JLabel();
		lbLaptop17 = new JLabel();
		lbLaptop18 = new JLabel();
		lbLaptop19 = new JLabel();
		lbLaptop20 = new JLabel();
		lbLaptop21 = new JLabel();
		lbLaptop22 = new JLabel();
		lbLaptop23 = new JLabel();
		lbLaptop24 = new JLabel();
		lbLaptop25 = new JLabel();
		lbLaptop[0] = lbLaptop01;
		lbLaptop[1] = lbLaptop02;
		lbLaptop[2] = lbLaptop03;
		lbLaptop[3] = lbLaptop04;
		lbLaptop[4] = lbLaptop05;
		lbLaptop[5] = lbLaptop06;
		lbLaptop[6] = lbLaptop07;
		lbLaptop[7] = lbLaptop08;
		lbLaptop[8] = lbLaptop09;
		lbLaptop[9] = lbLaptop10;
		lbLaptop[10] = lbLaptop11;
		lbLaptop[11] = lbLaptop12;
		lbLaptop[12] = lbLaptop13;
		lbLaptop[13] = lbLaptop14;
		lbLaptop[14] = lbLaptop15;
		lbLaptop[15] = lbLaptop16;
		lbLaptop[16] = lbLaptop17;
		lbLaptop[17] = lbLaptop18;
		lbLaptop[18] = lbLaptop19;
		lbLaptop[19] = lbLaptop20;
		lbLaptop[20] = lbLaptop21;
		lbLaptop[21] = lbLaptop22;
		lbLaptop[22] = lbLaptop23;
		lbLaptop[23] = lbLaptop24;
		lbLaptop[24] = lbLaptop25;

		// �˾��Ŵ�
		jpm = new JPopupMenu();
		popItemStart = new JMenuItem("�����ϱ�");
		popItemPay = new JMenuItem("�����ϱ�");
		popItemCharge = new JMenuItem("�����ϱ�");
		popItemChat = new JMenuItem("ä���ϱ�");
		popItemName = new JMenuItem("���Ӹ�");
		jpm.add(popItemName);
		jpm.addSeparator();
		jpm.add(popItemStart);
		jpm.add(popItemPay);
		jpm.add(popItemCharge);
		jpm.add(popItemChat);

		popItemPay.addActionListener(actionHandler);
		popItemStart.addActionListener(actionHandler);

		// �� �迭�� �̿��Ͽ� �̹��� ������ ���� + �������� + ���콺 ������ �߰�
		// Line1�� �߰�
		for (int i=0 ; i < 5; i++)
		{
			if(i==3)
			{
				lbBlank1.setIcon(imgBlank);
				plLine1.add(lbBlank1);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine1.add(lbLaptop[i]);
		}
		//Line2
		for (int i = 5; i < 10; i++)
		{
			if(i==8)
			{
				System.out.println("8���϶�");
				lbBlank2.setIcon(imgBlank);
				plLine2.add(lbBlank2);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine2.add(lbLaptop[i]);
		}
		
		//Line3
		for (int i = 10; i < 15; i++)
		{
			if(i==13)
			{
				lbBlank3.setIcon(imgBlank);
				plLine3.add(lbBlank3);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine3.add(lbLaptop[i]);
		}
		
		//Line4
		for (int i = 15; i < 20; i++)
		{
			if(i==18)
			{
				lbBlank4.setIcon(imgBlank);
				plLine4.add(lbBlank4);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine4.add(lbLaptop[i]);
		}
		
		//Line5
		for (int i = 20; i < 25; i++)
		{
			if(i==23)
			{
				lbBlank5.setIcon(imgBlank);
				plLine5.add(lbBlank5);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine5.add(lbLaptop[i]);
		}

		setVisible(true);
	}

	class MouseHandler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			// TODO Auto-generated method stub
			if (e.isMetaDown()) // ���콺�� ��Ŭ���ϸ�
			{
				Object obj = e.getSource();
				for (int i = 0; i < 25; i++) // ��ǻ�� �� ������ŭ ���� ����
				{
					if (obj.equals(lbLaptop[i])) // ���� ��ġ�ϴ� ���� ���� ���
					{
						popItemName.setText((i + 1) + "�� PC"); // �˾��Ŵ��� pc�̸� ǥ��
						jpm.setLocation(e.getXOnScreen(), e.getYOnScreen()); // ��ũ����
																				// ��������
																				// x��
																				// y��
																				// ��ǥ
																				// ������
																				// �˾��Ŵ�
																				// ��ġ����
						jpm.setVisible(true); // �˾��Ŵ� ���Ͷ�
						currentPcNumber = i; // ���� PC��ȣ�� ����
						System.out.println("laptop : " + currentPcNumber);
						break;// �� ���� �ʿ� ������ break;
					}
				}
			} else
			{
				jpm.setVisible(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}
	}

	class ActionHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object obj = e.getSource();

			// �˾��� PC������ ������
			if (obj.equals(popItemStart))
			{
				// ������ pc��ȣ�� ���� �������� ������ ������ �迭�� ��, �����ִ��� �����ִ��� �Ǵ�
				// (ex) 1�� �Ǿ� lbLaptop[0] 1�� �������� ������ �迭 imgLaptopR[0] / 1�� �����ִ�
				// ������ �迭 imgLaptopS[0]
				if (imgLaptopR[currentPcNumber]
						.equals(lbLaptop[currentPcNumber].getIcon()))
				{
					System.out.println(currentPcNumber + "�� PC�� �̹� ���� ���Դϴ�.");
					String message = (currentPcNumber + 1)
							+ "�� PC�� �̹� ���� ���Դϴ�.";
					// �˸� ���̾�α� ���
					jop.showMessageDialog(null, message, "�˸�",
							JOptionPane.INFORMATION_MESSAGE);
					jpm.setVisible(false); // �˾��Ŵ� ������
					currentPcNumber = -1;
				} else if (imgLaptopS[currentPcNumber]
						.equals(lbLaptop[currentPcNumber].getIcon()))
				{
					System.out.println(currentPcNumber + 1 + "�� PC����");
					// ���� ���������� ����
					lbLaptop[currentPcNumber]
							.setIcon(imgLaptopR[currentPcNumber]);
					jpm.setVisible(false); // �˾��Ŵ� ����
					currentPcNumber = -1;
				}
			}

			// �˾������ư
			if (obj.equals(popItemPay))
			{
				if (imgLaptopR[currentPcNumber]
						.equals(lbLaptop[currentPcNumber].getIcon()))
				{
					int result = jop.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "�˸�",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION)
					{
						System.out.println(currentPcNumber + 1 + "�� PC����");
						lbLaptop[currentPcNumber]
								.setIcon(imgLaptopS[currentPcNumber]);
						jpm.setVisible(false);
						currentPcNumber = -1;
					}
					jpm.setVisible(false);
					currentPcNumber = -1;
				} else
				{
					System.out.println(currentPcNumber + "�� PC�� ���� ���� �ƴմϴ�.");
					String message = (currentPcNumber + 1)
							+ "�� PC�� ���� ���� �ƴմϴ�.";
					jop.showMessageDialog(null, message, "�˸�",
							JOptionPane.INFORMATION_MESSAGE);
					jpm.setVisible(false);
					currentPcNumber = -1;
				}
			}

			// �⺻ �Ŵ� ���� ��ư
			if (obj.equals(itemBasicExit))
			{
				int result = jop.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "�˸�",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		}
	}

	public static void main(String[] args)
	{
		new FrameServer();
	}
}
