package vistas;

import SocketServidorTCP.HiloConexiones;
import SocketServidorTCP.Servidor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Ventana extends JFrame implements ActionListener {

    JButton btnIniciar;
    JButton btnFinalizar;
    JTextField nroPuerto;
    JTextArea textBox;
    JRadioButton radioBoton;
    Servidor server;    
    private HiloConexiones secretaria;

    public Ventana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.btnIniciar = new JButton("Iniciar");
        this.btnIniciar.setBounds(10, 10, 150, 30);
        this.btnIniciar.addActionListener(this);
        add(this.btnIniciar);

        this.nroPuerto = new JTextField();
        this.nroPuerto.setBounds(170, 10, 150, 30);
        add(this.nroPuerto);

        this.btnFinalizar = new JButton("Finalizar");
        this.btnFinalizar.setBounds(10, 50, 150, 30);
        this.btnFinalizar.addActionListener(this);
        add(this.btnFinalizar);

        this.textBox = new JTextArea();
        this.textBox.setBounds(10, 90, 310, 310);
        add(this.textBox);

        this.radioBoton = new JRadioButton();
        this.radioBoton.setName("Estado");
        this.radioBoton.setBounds(200, 60, 20, 20);
        this.radioBoton.addActionListener(this);
        add(this.radioBoton);

        setLayout(null);
        setSize(350, 450);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnIniciar) {
            this.server = new Servidor(Integer.parseInt(this.nroPuerto.getText()));
            IniciarServidor();
            System.out.println("Servidor iniciado!!!");
        } else if (e.getSource() == this.btnFinalizar) {
            //pararServidor();
            System.out.println("Servidor detenido...");
        } else if (e.getSource() == this.radioBoton) {
            if (this.radioBoton.isSelected()) {
                this.server = new Servidor(Integer.parseInt(this.nroPuerto.getText()));
                IniciarServidor();
                System.out.println("Servidor iniciado!!!");
            } else {
                System.out.println("No esta activado");
            }
        }else {
                System.out.println("No esta activado");
            }
    }

    public void IniciarServidor() {
        this.server.iniciarServer();
    }
    public void pararServidor() {
        this.server.stopServer();
    }

}
