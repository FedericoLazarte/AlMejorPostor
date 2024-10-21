package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import controlador.Controlador;
import logica.Oferta;

public class Main {

	private JFrame frameInicio;
	private JLabel labelTitulo;
	private JButton boton;
	private JButton boton2;
	private Controlador controlador; // Crear instancia del controlador
	private JTextField textHoraInicio;
	private JTextField textHoraFinal;
	private JTextField textOferta;
	private JPanel panel;
	private JTextArea textAreaOfertas; // Área de texto para mostrar las ofertas
	private JButton botonCargarSerializadas;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frameInicio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		controlador = new Controlador(); // Inicializar el controlador
		initialize();
	}

	private void initialize() {
		crearFrameInicio();
	}

	private void crearFrameInicio() {
		frameInicio = new JFrame();
		frameInicio.setBackground(Color.WHITE);
		frameInicio.setTitle("¡¡Al mejor Postor!!");
		frameInicio.setBounds(100, 100, 700, 700);
		frameInicio.getContentPane().setBackground(Color.WHITE);
		frameInicio.setLocationRelativeTo(null);
		frameInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameInicio.getContentPane().setLayout(null);

		crearTitulo();
		crearIngreso(); // no se me ocurrio un mejor nombre para el lugar donde te deja crear una nueva
						// ofera (son las 2 de la mañana loco jaja)
		crearBotonParaOfertar();
		crearBotonParaIniciarOferta();
		crearBotonParaCargarSerializados();
		crearPanelDeOfertasActuales();

	}

	private void crearTitulo() {
		labelTitulo = new JLabel("Al mejor postor");
		labelTitulo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		labelTitulo.setHorizontalAlignment(JLabel.CENTER);
		labelTitulo.setOpaque(false);
		labelTitulo.setBounds(70, 50, 550, 100);
		frameInicio.getContentPane().add(labelTitulo);
	}

	private void crearIngreso() {
		textHoraInicio = new JTextField();
		textHoraInicio.setBounds(545, 328, 108, 20);
		frameInicio.getContentPane().add(textHoraInicio);
		textHoraInicio.setColumns(10);

		textHoraFinal = new JTextField();
		textHoraFinal.setBounds(545, 359, 108, 20);
		frameInicio.getContentPane().add(textHoraFinal);
		textHoraFinal.setColumns(10);

		textOferta = new JTextField();
		textOferta.setBounds(545, 390, 108, 20);
		frameInicio.getContentPane().add(textOferta);
		textOferta.setColumns(10);

		JLabel horaEntrada = new JLabel("Hora de entrada:");
		horaEntrada.setBounds(425, 328, 108, 20);
		frameInicio.getContentPane().add(horaEntrada);

		JLabel HoraSalida = new JLabel("Hora de salida:");
		HoraSalida.setBounds(425, 359, 102, 20);
		frameInicio.getContentPane().add(HoraSalida);

		JLabel Oferta = new JLabel("Oferta por el horario:");
		Oferta.setBounds(425, 390, 114, 20);
		frameInicio.getContentPane().add(Oferta);
	}

	private void crearBotonParaOfertar() {
		boton = new JButton("Cargar Oferta");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener los valores de los campos de texto
					int horaInicio = Integer.parseInt(textHoraInicio.getText());
					int horaFin = Integer.parseInt(textHoraFinal.getText());
					int monto = Integer.parseInt(textOferta.getText());

					// Pasar la nueva oferta al controlador para agregarla
					Oferta oferta = new Oferta(horaInicio, horaFin, monto);
					controlador.crearOferta(oferta);

					// muestra la nueva oferta en pantalla
					mostrarOfertas();

				} catch (NumberFormatException ex) {
					// Manejar el error si los valores ingresados no son válidos
					System.out.println("Error: Asegúrese de ingresar números válidos.");
				}
			}
		});
		boton.setBounds(446, 449, 207, 23);
		frameInicio.getContentPane().add(boton);
	}

	private void crearBotonParaIniciarOferta() {
		boton2 = new JButton("Algoritmo goloso");
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.iniciarOfertas();
			}
		});
		boton2.setBounds(148, 600, 399, 23);
		frameInicio.getContentPane().add(boton2);
	}
	
	private void crearBotonParaCargarSerializados() {
		botonCargarSerializadas = new JButton("Cargar Ofertas Serializadas");
        botonCargarSerializadas.setBounds(446, 523, 207, 23); 
        frameInicio.getContentPane().add(botonCargarSerializadas);
        botonCargarSerializadas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarOfertas();
            }
        });
	}

//este metodo se hizo largo por el scroll
	private void crearPanelDeOfertasActuales() {
		panel = new JPanel();
		panel.setBounds(29, 323, 373, 225);
		panel.setLayout(new BorderLayout()); // Usar BorderLayout para el panel

		// Crear el JTextArea
		textAreaOfertas = new JTextArea();
		textAreaOfertas.setEditable(false); // No se puede editar
		textAreaOfertas.setLineWrap(true); // Ajustar línea
		textAreaOfertas.setWrapStyleWord(true); // Ajustar palabras

		// Crear el JScrollPane y agregar el JTextArea
		JScrollPane scrollPane = new JScrollPane(textAreaOfertas);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Siempre mostrar la barra de
																						// desplazamiento vertical
		scrollPane.setPreferredSize(new Dimension(373, 225)); // Tamaño preferido para el JScrollPane

		// Agregar el JScrollPane al panel
		panel.add(scrollPane, BorderLayout.CENTER);

		// Agregar el panel al frame
		frameInicio.getContentPane().add(panel);

		JButton btnOfertasPredeterminadas = new JButton("Ofertas predeterminadas");
		btnOfertasPredeterminadas.setBounds(446, 489, 207, 23);
		frameInicio.getContentPane().add(btnOfertasPredeterminadas);
		btnOfertasPredeterminadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.ofertasPredeterminadas();
				mostrarOfertas();
			}
		});
	}

	private void mostrarOfertas() {
		List<String> ofertasTexto = controlador.obtenerOfertasComoTexto();
		textAreaOfertas.setText(""); // Limpiar el JTextArea
		for (String ofertaTexto : ofertasTexto) {
			textAreaOfertas.append(ofertaTexto + "\n");
		}

	}
}
