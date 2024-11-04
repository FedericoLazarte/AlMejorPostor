package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

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
	private JPanel panelOfertasAdjudicadas;
	private JPanel panelOfertasRegistradas;
	private JTextArea textAreaRegistradas; // Área de texto para mostrar las ofertas registradas
	private JTextArea textAreaAdjudicadas; // Área de texto para mostrar las ofertas adjudicadas
	private JButton botonCargarSerializadas;
	private JTextField textNombreOfertante;
	private JTextField textEquipamiento;

	private JDateChooser dateChooser;
	
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
		crearCalendario();
		crearPanelDeOfertasAdjudicadas();
		crearPanelDeOfertasRegistradas();
		
		crearImagenFondoFrame();

	}
	
	private void crearImagenFondoFrame() {
		ImageIcon imagen = new ImageIcon("src/imagenFondo/FondoTP3.png");
		Image imagenEscalada = imagen.getImage().getScaledInstance(frameInicio.getWidth(), frameInicio.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel etiquetaImagen = new JLabel(imagenRedimensionada);
		etiquetaImagen.setBounds(0, 0, frameInicio.getWidth(), frameInicio.getHeight());

		// Añadir al fondo en la primera posición (índice cero)
		frameInicio.getContentPane().add(etiquetaImagen);
		frameInicio.getContentPane().setComponentZOrder(etiquetaImagen, frameInicio.getContentPane().getComponentCount() - 1);
	}

	private void crearTitulo() {
		labelTitulo = new JLabel("¡Al mejor postor!");
		labelTitulo.setFont(new Font("Tahoma", Font.PLAIN, 42));
		labelTitulo.setHorizontalAlignment(JLabel.CENTER);
		labelTitulo.setOpaque(false);
		labelTitulo.setBounds(70, 50, 550, 100);
		frameInicio.getContentPane().add(labelTitulo);
	}

	private void crearIngreso() {
		textHoraInicio = new JTextField();
		textHoraInicio.setBounds(550, 328, 124, 20);
		frameInicio.getContentPane().add(textHoraInicio);
		textHoraInicio.setColumns(10);

		textHoraFinal = new JTextField();
		textHoraFinal.setBounds(550, 359, 124, 20);
		frameInicio.getContentPane().add(textHoraFinal);
		textHoraFinal.setColumns(10);

		textOferta = new JTextField();
		textOferta.setBounds(550, 390, 124, 20);
		frameInicio.getContentPane().add(textOferta);
		textOferta.setColumns(10);

		textNombreOfertante = new JTextField();
		textNombreOfertante.setBounds(550, 300, 124, 20);
		frameInicio.getContentPane().add(textNombreOfertante);
		textNombreOfertante.setColumns(10);

		textEquipamiento = new JTextField();
		textEquipamiento.setBounds(550, 421, 124, 20);
		frameInicio.getContentPane().add(textEquipamiento);
		textEquipamiento.setColumns(10);

		JLabel nombreOfertante = new JLabel("Nombre:");
		nombreOfertante.setBounds(412, 300, 108, 20);
		frameInicio.getContentPane().add(nombreOfertante);

		JLabel equipamientoSolicitado = new JLabel("Equipamiento:");
		equipamientoSolicitado.setBounds(412, 421, 142, 20);
		frameInicio.getContentPane().add(equipamientoSolicitado);

		JLabel horaEntrada = new JLabel("Hora de entrada:");
		horaEntrada.setBounds(412, 328, 108, 20);
		frameInicio.getContentPane().add(horaEntrada);

		JLabel HoraSalida = new JLabel("Hora de salida:");
		HoraSalida.setBounds(412, 359, 102, 20);
		frameInicio.getContentPane().add(HoraSalida);

		JLabel Oferta = new JLabel("Oferta por el horario:");
		Oferta.setBounds(412, 390, 119, 20);
		frameInicio.getContentPane().add(Oferta);
	}

	private void crearBotonParaOfertar() {
		boton = new JButton("Cargar Oferta");
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Obtener los valores de los campos de texto
					String nombreOfertante = textNombreOfertante.getText();
					int horaInicio = Integer.parseInt(textHoraInicio.getText());
					int horaFin = Integer.parseInt(textHoraFinal.getText());
					int monto = Integer.parseInt(textOferta.getText());
					String equipamiento = textEquipamiento.getText();

					// Pasar la nueva oferta al controlador para agregarla
					Oferta oferta = new Oferta(horaInicio, horaFin, monto, nombreOfertante, equipamiento);
					controlador.crearOferta(oferta);

					// muestra la nueva oferta en pantalla
					mostrarOfertasRegistradas();

				} catch (NumberFormatException ex) {
					// Manejar el error si los valores ingresados no son válidos
					System.out.println("Error: Asegúrese de ingresar números válidos.");
				}
			}
		});
		boton.setBounds(446, 474, 207, 23);
		frameInicio.getContentPane().add(boton);
	}

	private void crearBotonParaIniciarOferta() {
		boton2 = new JButton("Algoritmo goloso");
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarOfertasAdjudicadas();
			}
		});
		boton2.setBounds(148, 600, 399, 23);
		frameInicio.getContentPane().add(boton2);
	}

	private void crearBotonParaCargarSerializados() {
		botonCargarSerializadas = new JButton("Cargar Ofertas Serializadas");
		botonCargarSerializadas.setBounds(446, 525, 207, 23);
		frameInicio.getContentPane().add(botonCargarSerializadas);
		botonCargarSerializadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarOfertasRegistradas();
			}
		});
	}

	private void crearCalendario() {
		dateChooser = new JDateChooser();
		dateChooser.setBounds(29, 290, 200, 30);
		frameInicio.getContentPane().add(dateChooser);
	}
	
	private void crearPanelDeOfertasAdjudicadas(){
		panelOfertasAdjudicadas = new JPanel();
		panelOfertasAdjudicadas.setBounds(29, 323, 373, 120);
		panelOfertasAdjudicadas.setLayout(new BorderLayout());
		
		// Crear el JTextArea
		textAreaAdjudicadas = new JTextArea();
		textAreaAdjudicadas.setEditable(false); // No se puede editar
		textAreaAdjudicadas.setLineWrap(true); // Ajustar línea
		textAreaAdjudicadas.setWrapStyleWord(true); // Ajustar palabras
		
		// Crear el JScrollPane y agregar el JTextArea
		JScrollPane scrollPane = new JScrollPane(textAreaAdjudicadas);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Siempre mostrar la barra de
																						// desplazamiento vertical
		scrollPane.setPreferredSize(new Dimension(373, 225)); // Tamaño preferido para el JScrollPane

		// Agregar el JScrollPane al panel
		panelOfertasAdjudicadas.add(scrollPane, BorderLayout.CENTER);
		
		frameInicio.getContentPane().add(panelOfertasAdjudicadas);
	}
	
	//este metodo se hizo largo por el scroll
	private void crearPanelDeOfertasRegistradas() {
		panelOfertasRegistradas = new JPanel();
		panelOfertasRegistradas.setBounds(29, 450, 373, 120);
		panelOfertasRegistradas.setLayout(new BorderLayout()); // Usar BorderLayout para el panel

		// Crear el JTextArea
		textAreaRegistradas = new JTextArea();
		textAreaRegistradas.setEditable(false); // No se puede editar
		textAreaRegistradas.setLineWrap(true); // Ajustar línea
		textAreaRegistradas.setWrapStyleWord(true); // Ajustar palabras

		// Crear el JScrollPane y agregar el JTextArea
		JScrollPane scrollPane = new JScrollPane(textAreaRegistradas);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Siempre mostrar la barra de
																						// desplazamiento vertical
		scrollPane.setPreferredSize(new Dimension(373, 225)); // Tamaño preferido para el JScrollPane

		// Agregar el JScrollPane al panel
		panelOfertasRegistradas.add(scrollPane, BorderLayout.CENTER);

		// Agregar el panel al frame
		frameInicio.getContentPane().add(panelOfertasRegistradas);
	}
	
	private void mostrarOfertasAdjudicadas() {
		textAreaAdjudicadas.setText("--Ofertas Adjudicadas--\n"); // Limpiar el JTextArea
		textAreaAdjudicadas.append(controlador.obtenerAdjudicadasComoTexto());

	}
	
	private void mostrarOfertasRegistradas() {
		textAreaRegistradas.setText("--Ofertas Registradas--\n"); // Limpiar el JTextArea
		textAreaRegistradas.append(controlador.obtenerRegistradasComoTexto());
		

	}
}