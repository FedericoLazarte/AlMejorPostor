package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import controlador.Controlador;
import logica.Oferta;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.beans.PropertyChangeEvent;

public class Main {
	private JFrame frameInicio;
	private JLabel labelTitulo;
	private JButton boton;
	private JButton boton2;
	private Controlador controlador; 
	private JTextField textHoraInicio;
	private JTextField textHoraFinal;
	private JTextField textOferta;
	private JPanel panelOfertasAdjudicadas;
	private JPanel panelOfertasRegistradas;
	private JTextArea textAreaRegistradas; 
	private JTextArea textAreaAdjudicadas;
	private JButton botonCargarSerializadas;
	private JTextField textNombreOfertante;
	private JTextField textEquipamiento;
	private JDateChooser calendario;
	private Date fechaSeleccionada;
	
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
		controlador = new Controlador(); 
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
		crearIngreso(); 		
		crearBotonParaOfertar();
		crearBotonParaIniciarOferta();
		crearBotonParaCargarSerializados();
		crearBotonOfertasOptimas();
		crearPanelDeOfertasAdjudicadas();
		crearPanelDeOfertasRegistradas();	
		crearCalendario();	
		crearImagenFondoFrame();

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
	                String nombreOfertante = textNombreOfertante.getText();
	                int horaInicio = Integer.parseInt(textHoraInicio.getText());
	                int horaFin = Integer.parseInt(textHoraFinal.getText());
	                int monto = Integer.parseInt(textOferta.getText());
	                String equipamiento = textEquipamiento.getText();
	                fechaSeleccionada = calendario.getDate();
	                if (fechaSeleccionada == null) {
	                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha válida.", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
	                    return; 
	                }
	                Oferta oferta = new Oferta(horaInicio, horaFin, monto, nombreOfertante, equipamiento, fechaSeleccionada);
	                controlador.crearOferta(oferta);
	                mostrarOfertasRegistradas();  
	                mostrarOfertasDeFecha(fechaSeleccionada);;
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Asegúrese de ingresar números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	            } catch (IllegalArgumentException ex) { 
	                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Horario", JOptionPane.ERROR_MESSAGE);
	            } catch (NullPointerException ex) {
	                JOptionPane.showMessageDialog(null, "Ingrese una fecha válida.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
		boton.setBounds(446, 474, 207, 23);
		frameInicio.getContentPane().add(boton);
	}
	
	private void crearBotonParaCargarSerializados() {
		botonCargarSerializadas = new JButton("Cargar Ofertas Serializadas");
		botonCargarSerializadas.setBounds(446, 525, 207, 23);
		frameInicio.getContentPane().add(botonCargarSerializadas);
		botonCargarSerializadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarOfertasSerializadas();
				mostrarOfertasRegistradas();
			}
		});
	}
	
	private void mostrarOfertasRegistradas() {
		textAreaAdjudicadas.setText("--Ofertas Registradas--\n");
		textAreaAdjudicadas.append(controlador.obtenerOfertasRegistradas());
	}
	
	private void mostrarOfertasDeFecha(Date fechaDeOfertas) {
		textAreaRegistradas.setText("--Ofertas de la fecha--\n"); 
		textAreaRegistradas.append(controlador.obtenerOfertasDeFecha(fechaDeOfertas));
	}
	
	private void cargarOfertasSerializadas() {
		this.controlador.cargarOfertasSerializadas();
	}

	private void crearBotonParaIniciarOferta() {
		boton2 = new JButton("Obtener ofertas adjudicadas");
		boton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechaSeleccionada = calendario.getDate();
				controlador.obtenerAdjudicadasComoTexto(fechaSeleccionada);
			}
		});

		boton2.setBounds(148, 591, 399, 23);
		frameInicio.getContentPane().add(boton2);
	}
	
	private void crearPanelDeOfertasAdjudicadas(){
		panelOfertasAdjudicadas = new JPanel();
		panelOfertasAdjudicadas.setBounds(29, 290, 373, 120);
		panelOfertasAdjudicadas.setLayout(new BorderLayout());
		textAreaAdjudicadas = new JTextArea();
		textAreaAdjudicadas.setEditable(false); 
		textAreaAdjudicadas.setLineWrap(true); 
		textAreaAdjudicadas.setWrapStyleWord(true); 
		JScrollPane scrollPane = new JScrollPane(textAreaAdjudicadas);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 																		
		scrollPane.setPreferredSize(new Dimension(373, 225)); 
		panelOfertasAdjudicadas.add(scrollPane, BorderLayout.CENTER);	
		frameInicio.getContentPane().add(panelOfertasAdjudicadas);
	}
	
	private void crearPanelDeOfertasRegistradas() {
		panelOfertasRegistradas = new JPanel();
		panelOfertasRegistradas.setBounds(29, 450, 373, 120);
		panelOfertasRegistradas.setLayout(new BorderLayout());
		textAreaRegistradas = new JTextArea();
		textAreaRegistradas.setEditable(false); 
		textAreaRegistradas.setLineWrap(true); 
		textAreaRegistradas.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textAreaRegistradas);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 																		
		scrollPane.setPreferredSize(new Dimension(373, 225)); 
		panelOfertasRegistradas.add(scrollPane, BorderLayout.CENTER);
		frameInicio.getContentPane().add(panelOfertasRegistradas);
	}
	
	private void crearCalendario() {
		calendario = new JDateChooser();
		prestablecerFecha("2024-11-01");
		calendario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				fechaSeleccionada = calendario.getDate();
				mostrarOfertasDeFecha(fechaSeleccionada);
			}
		});
		calendario.setBounds(29, 421, 200, 30);					
		frameInicio.getContentPane().add(calendario);	
	}
	
	private void crearImagenFondoFrame() {
		ImageIcon imagen = new ImageIcon("src/imagenFondo/FondoTP3.png");
		Image imagenEscalada = imagen.getImage().getScaledInstance(frameInicio.getWidth(), frameInicio.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
		JLabel etiquetaImagen = new JLabel(imagenRedimensionada);
		etiquetaImagen.setBounds(0, 0, frameInicio.getWidth(), frameInicio.getHeight());
		frameInicio.getContentPane().add(etiquetaImagen);
		frameInicio.getContentPane().setComponentZOrder(etiquetaImagen, frameInicio.getContentPane().getComponentCount() - 1);
	}

	private void prestablecerFecha(String fecha) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date fechaPreestablecida = formatoFecha.parse(fecha); //Formatea la fecha lejible a una tipo Date
			calendario.setDate(fechaPreestablecida); 
		} catch(ParseException e){		
		}
	}	
	
	private void crearBotonOfertasOptimas() {
	    JButton btnObtenerOfertasOptimas = new JButton("Obtener ofertas óptimas (DAG)");
	    btnObtenerOfertasOptimas.setBounds(148, 627, 399, 23);
	    frameInicio.getContentPane().add(btnObtenerOfertasOptimas);
	    btnObtenerOfertasOptimas.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            List<Oferta> ofertasOptimas = controlador.obtenerCaminoMaximoEnGrafo();
	            if (!ofertasOptimas.isEmpty()) {
	                StringBuilder mensaje = new StringBuilder();
	                mensaje.append("Ofertas óptimas encontradas usando DAG:\n\n");
	                double gananciaTotal = 0;
	                
	                for (Oferta oferta : ofertasOptimas) {
	                    mensaje.append(oferta.toString()).append("\n");
	                    gananciaTotal += oferta.getMonto();
	                }
	                
	                mensaje.append("\nGanancia total: $").append(gananciaTotal);
	                JOptionPane.showMessageDialog(frameInicio, mensaje.toString(), 
	                    "Ofertas Óptimas (DAG)", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(frameInicio, 
	                    "No se encontraron ofertas óptimas.", 
	                    "Ofertas Óptimas (DAG)", 
	                    JOptionPane.INFORMATION_MESSAGE);
	            }
	        }
	    });
	}
}