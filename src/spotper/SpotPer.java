package spotper;

import java.sql.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SpotPer {
		
	public static void main (String[] args) {
		
		Connection connection = null;
        String url = "jdbc:sqlserver://localhost;databaseName=BDSpotPer;integratedSecurity=true";

        try
        {
        	
        connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        
        JFrame frame = new JFrame("BDSpotPer");
        CardLayout cardLayout = new CardLayout();
        
        //TELA PRINCIPAL (MANUTENÇÃO DE PLAYLISTS)
        
        String listaPlaylists = "<html>  <b>Playlists</b> <br><br> Código - Nome - Data de Criação<br><br>";
        ResultSet rs = statement.executeQuery("SELECT cod_p, nome, data_de_criacao FROM playlist");
        while (rs.next()) {
            listaPlaylists += (rs.getString("cod_p") + " - " + rs.getString("nome") + " - " + rs.getDate("data_de_criacao") + "<br>");
        }
        rs.close();
        
        listaPlaylists += "</html>";
        
        
        JLabel labelListaPlaylists = new JLabel(listaPlaylists);
        labelListaPlaylists.setBounds(10,10,500,480);
        labelListaPlaylists.setVerticalAlignment(JLabel.TOP);
        labelListaPlaylists.setFont(new Font("Verdana", Font.PLAIN, 12));
        Border border = BorderFactory.createLineBorder(Color.ORANGE);
        labelListaPlaylists.setBorder(border);
        
        JButton alterarPlaylist = new JButton();
        alterarPlaylist.setText("Alterar Playlist");
        alterarPlaylist.setBounds(270, 510, 200, 50);
        
        alterarPlaylist.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(frame.getContentPane(), "painelDeAlteracaoDePlaylist");
				
            }	
        
		 });
        
        JButton addPlaylist = new JButton();
        addPlaylist.setBounds(10, 510, 200, 50);
        addPlaylist.setText("Adicionar Playlist");
        
        addPlaylist.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(frame.getContentPane(), "painelAdicionarPlaylist");          
				
            }	
        
		 });
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBounds(0,0,565,620);
        painelPrincipal.setLayout(null);
        painelPrincipal.add(labelListaPlaylists);
        painelPrincipal.add(alterarPlaylist);
        painelPrincipal.add(addPlaylist);
        
        //PAINEL DE ALTERAÇÃO DE PLAYLIST
        
        JButton voltar = new JButton();
        voltar.setBounds(10, 510, 200, 50);
        voltar.setText("Voltar");
        
        voltar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(frame.getContentPane(), "painelPrincipal");
				
            }	
        
		 });
        
        
        JLabel labelCodPlaylist = new JLabel("Código da Playlist:");
        labelCodPlaylist.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelCodPlaylist.setBounds(10,10,150,50);
        
        String listaMusicas = "<html>  <b>Músicas</b> <br><br> Código - Número da Faixa - Descrição - Álbum<br><br>";
        
        JTextArea textCodPlaylist = new JTextArea();
        textCodPlaylist.setBounds(150,25,100,25);
		
        
        JLabel labelListaMusicas = new JLabel(listaMusicas);
        labelListaMusicas.setBounds(10,90,500,320);
        labelListaMusicas.setVerticalAlignment(JLabel.TOP);
        labelListaMusicas.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelListaMusicas.setBorder(border);
        
        JButton send1 = new JButton("Consultar");
		send1.setBounds(280,20,150,30);
        
        send1.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		
                String listaMusicas = "<html>  <b>Músicas</b> <br><br> Código - Número da Faixa - Descrição - Álbum<br><br>";
                ResultSet rs;
				try {
					rs = statement.executeQuery("SELECT f.cod_album, f.num_faixa, f.descricao AS desc_f, a.descricao AS desc_alb  FROM faixa f, album a, faixa_play fp WHERE f.cod_album=a.cod AND f.cod_album=fp.cod_album AND f.num_faixa=fp.num_faixa AND fp.cod_p=" + textCodPlaylist.getText());
					while (rs.next()) {
	                    listaMusicas += (rs.getString("cod_album") + "." + rs.getInt("num_faixa") + " - " + rs.getInt("num_faixa") + " - " + rs.getString("desc_f") + " - " + rs.getString("desc_alb") + "<br>");
	                }
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                
                listaMusicas += "</html>";
                
                labelListaMusicas.setText(listaMusicas);
				
            }	
        
		 });
        
        JLabel labelCodFaixa = new JLabel("Código da Faixa:");
        labelCodFaixa.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelCodFaixa.setBounds(10,440,150,50);
        
        JTextArea textCodFaixa = new JTextArea();
        textCodFaixa.setBounds(150,455,100,25);
        
        JButton remove = new JButton("Remover");
		remove.setBounds(280,455,150,30);
        
        remove.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		
        		try {
        			
        			String text = textCodFaixa.getText();
    				
    				String[] textSplit = text.split("\\.");
    				
    				String cod_p = textCodPlaylist.getText();
    				String cod_album = textSplit[0]; 
    				String num_faixa = textSplit[1];	
        			
        			statement.executeUpdate("DELETE FROM faixa_play WHERE cod_p='" + cod_p + "' AND cod_album='" + cod_album + "' AND num_faixa='" + num_faixa + "'");
        			
        		} catch (Exception e1) {
        			e1.printStackTrace();
        		}
        		
        	}
        	
        });
        
        JButton addMusica = new JButton();
        addMusica.setBounds(270, 510, 200, 50);
        addMusica.setText("Adicionar Músicas");
        
        JLabel labelListaAlbunsMusicas2 = new JLabel();
        
        addMusica.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(frame.getContentPane(), "painelAddMusicas");
                
                String listaAlbunsMusicas = "<html>  <b>Álbuns e Músicas</b> <br>";
                
                
        		try {
        			ResultSet rs1;
        			rs1 = statement.executeQuery("SELECT DISTINCT a.cod, a.descricao AS desc_a, f.num_faixa, f.descricao AS desc_f FROM album a, faixa f, faixa_play fp WHERE a.cod=f.cod_album AND NOT EXISTS (SELECT * FROM faixa_play fp1 WHERE fp1.cod_album=f.cod_album AND fp1.num_faixa=f.num_faixa AND fp1.cod_p='"+ textCodPlaylist.getText()  + "') ORDER BY a.descricao, f.num_faixa");

        			int lastAlbumCod = -1;
        			
        			while (rs1.next()) {
                  
                        if (rs1.getInt("cod") != lastAlbumCod) {
                        	listaAlbunsMusicas += ("<br>" + rs1.getString("desc_a") + "<br>");
                        }
                        
                        listaAlbunsMusicas += (rs1.getString("cod") + "." + rs1.getInt("num_faixa") + " - " + rs1.getInt("num_faixa") + " - " + rs1.getString("desc_f") + "<br>");
                        lastAlbumCod = rs1.getInt("cod");
        				
                    }
        			
        			rs1.close();
        			
        		} catch (SQLException e1) {
        			e1.printStackTrace();
        		}
        		        		
        		labelListaAlbunsMusicas2.setText(listaAlbunsMusicas);
                
				
            }	
        
		 });
        
        JPanel painelDeAlteracaoDePlaylist = new JPanel();
        painelDeAlteracaoDePlaylist.setBounds(0,0,565,620);
        painelDeAlteracaoDePlaylist.setLayout(null);
        painelDeAlteracaoDePlaylist.add(labelListaMusicas);
        painelDeAlteracaoDePlaylist.add(textCodPlaylist);
        painelDeAlteracaoDePlaylist.add(textCodFaixa);
        painelDeAlteracaoDePlaylist.add(voltar);
        painelDeAlteracaoDePlaylist.add(labelCodPlaylist);
        painelDeAlteracaoDePlaylist.add(send1);
        painelDeAlteracaoDePlaylist.add(labelCodFaixa);
        painelDeAlteracaoDePlaylist.add(remove);
        painelDeAlteracaoDePlaylist.add(addMusica);
        
        // PAINEL PARA ADICIONAR MÚSICAS
        
        JButton voltar3 = new JButton();
        voltar3.setBounds(10, 510, 200, 50);
        voltar3.setText("Voltar");
        
        voltar3.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(frame.getContentPane(), "painelDeAlteracaoDePlaylist");
				
            }	
        
		 });
                
        
        labelListaAlbunsMusicas2.setBounds(10,10,500,400);
        labelListaAlbunsMusicas2.setVerticalAlignment(JLabel.TOP);
        labelListaAlbunsMusicas2.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelListaAlbunsMusicas2.setBorder(border);
        
        JLabel labelCodFaixa2 = new JLabel("Código da Faixa:");
        labelCodFaixa2.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelCodFaixa2.setBounds(10,440,150,50);
        
        JTextArea textCodFaixa2 = new JTextArea();
        textCodFaixa2.setBounds(150,455,100,25);
        
        JButton add = new JButton("Adicionar");
		add.setBounds(280,455,150,30);
        
        add.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		
        		try {
        			
        			String text = textCodFaixa2.getText();
    				
    				String[] textSplit = text.split("\\.");
    				
    				String cod_p = textCodPlaylist.getText();
    				String cod_album = textSplit[0]; 
    				String num_faixa = textSplit[1];	
        			
        			statement.executeUpdate("INSERT INTO faixa_play VALUES ('" + cod_p + "','" + cod_album + "','" + num_faixa + "', 0, '01.01.0001')");
        			
        		} catch (Exception e1) {
        			e1.printStackTrace();
        		}
        		
        	}
        	
        });
        
        JPanel painelAddMusicas = new JPanel();
        painelAddMusicas.setBounds(0,0,565,620);
        painelAddMusicas.setLayout(null);
        painelAddMusicas.add(voltar3);
        painelAddMusicas.add(labelListaAlbunsMusicas2);
        painelAddMusicas.add(labelCodFaixa2);
        painelAddMusicas.add(textCodFaixa2);
        painelAddMusicas.add(add);
        
        // PAINEL DE CRIAÇÃO DE PLAYLIST
        
        JButton voltar2 = new JButton();
        voltar2.setBounds(10, 510, 200, 50);
        voltar2.setText("Voltar");
        
        voltar2.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(frame.getContentPane(), "painelPrincipal");
				
            }	
        
		 });
        
        JButton send2 = new JButton();
        send2.setBounds(300, 30, 200, 50);
        send2.setText("Criar Playlist");
        
        JLabel labelNomePlaylist = new JLabel("Nome da Playlist:");
        labelNomePlaylist.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelNomePlaylist.setBounds(10,30,150,50);
                
        JTextArea textNomePlaylist = new JTextArea();
        textNomePlaylist.setBounds(150,45,130,25);
        
        send2.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		
				try {
										
					statement.executeUpdate("INSERT INTO playlist VALUES ((SELECT p.cod_p + 1 FROM playlist p WHERE p.cod_p >= ALL(SELECT cod_p FROM playlist)),'" + textNomePlaylist.getText() + "','0', GETDATE())");
					
	        		String listaPlaylists = "<html>  <b>Playlists</b> <br><br> Código - Nome - Duração - Data de Criação<br><br>";
					ResultSet rs1 = statement.executeQuery("SELECT cod_p, nome, tempo_exe, data_de_criacao FROM playlist");
					while (rs1.next()) {
	                    listaPlaylists += (rs1.getString("cod_p") + " - " + rs1.getString("nome") + " - " + rs1.getInt("tempo_exe") + " - " + rs1.getDate("data_de_criacao") + "<br>");
	                }
	                rs1.close();
	                
	                listaPlaylists += "</html>";
	                labelListaPlaylists.setText(listaPlaylists);
	                
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
            }	
        	
		 });
        
        String listaAlbuns = "<html>  <b>Álbuns</b> <br><br> Código - Nome - Data de Gravação<br><br>";
        ResultSet rs2 = statement.executeQuery("SELECT cod, descricao, data_gravacao FROM album");
        while (rs2.next()) {
            listaAlbuns += (rs2.getString("cod") + " - " + rs2.getString("descricao") + " - " + rs2.getDate("data_gravacao") + "<br>");
        }
        rs2.close();

        
        JLabel labelListaAlbuns = new JLabel(listaAlbuns);
        labelListaAlbuns.setBounds(10,120,500,300);
        labelListaAlbuns.setVerticalAlignment(JLabel.TOP);
        labelListaAlbuns.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelListaAlbuns.setBorder(border);
		
		JLabel labelCodAlbumP = new JLabel("Código do Álbum:");
        labelCodAlbumP.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelCodAlbumP.setBounds(10,440,150,50);
        
        JTextArea textCodAlbumP = new JTextArea();
        textCodAlbumP.setBounds(150,455,100,25);
        
        JButton send3 = new JButton("Consultar");
		send3.setBounds(280,450,150,30);
		
		JLabel labelListaMusicasP = new JLabel();
		
        send3.addActionListener(new ActionListener() {
			
			@Override
            public void actionPerformed(ActionEvent e) {
				
				cardLayout.show(frame.getContentPane(), "painelAddMusicaNovaPlaylist");
				
				String listaMusicasP = "<html>  <b>Álbuns</b> <br>";
				
				listaMusicasP = "<html>  <b>Música</b> <br><br> Código - Descrição - Tempo de Execução<br><br>";
				ResultSet rs1;
				try {
					rs1 = statement.executeQuery("SELECT f.num_faixa, f.descricao, f.tempo_exe FROM faixa f, album a WHERE f.cod_album=a.cod AND a.cod='"+ textCodAlbumP.getText() +"' ORDER BY f.num_faixa");
					while (rs1.next()) {
	                    listaMusicasP += (rs1.getString("num_faixa") + " - " + rs1.getString("descricao") + " - " + rs1.getInt("tempo_exe") + "<br>");
	                }
	                rs1.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
                
                listaMusicasP += "</html>";
				
				labelListaMusicasP.setText(listaMusicasP);
				
				/*try {	
				String text = textCodAlbumP.getText();
				
				String[] textSplit = text.split("\\.");
				
				String cod_album = textSplit[0]; 
				String num_faixa = textSplit[1];	
				statement.executeUpdate("INSERT INTO faixa_play VALUES ((SELECT p.cod_p FROM playlist p WHERE p.cod_p >= ALL(SELECT cod_p FROM playlist)), '" + cod_album + "','" + num_faixa + "', 0, '01.01.0001')");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}*/
				
			}
			
		});
        
        JPanel painelAdicionarPlaylist = new JPanel();
        painelAdicionarPlaylist.setBounds(0,0,565,620);
        painelAdicionarPlaylist.setLayout(null);
        painelAdicionarPlaylist.add(voltar2);
        painelAdicionarPlaylist.add(send2);
        painelAdicionarPlaylist.add(labelNomePlaylist);
        painelAdicionarPlaylist.add(textNomePlaylist);
        painelAdicionarPlaylist.add(labelListaAlbuns);
        painelAdicionarPlaylist.add(labelCodAlbumP);
        painelAdicionarPlaylist.add(textCodAlbumP);
        painelAdicionarPlaylist.add(send3);
        
        
        //PAINEL PARA ADICIONAR MÚSICA A NOVA PLAYLIST
        
        JButton voltar4 = new JButton();
        voltar4.setBounds(10, 510, 200, 50);
        voltar4.setText("Voltar");
        
        voltar4.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(frame.getContentPane(), "painelAdicionarPlaylist");
				
            }	
        
		 });
        
        labelListaMusicasP.setBounds(10,10,500,400);
        labelListaMusicasP.setVerticalAlignment(JLabel.TOP);
        labelListaMusicasP.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelListaMusicasP.setBorder(border);
        
        JLabel labelCodFaixa3 = new JLabel("Código da Faixa:");
        labelCodFaixa3.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelCodFaixa3.setBounds(10,440,150,50);
        
        JTextArea textCodFaixa3 = new JTextArea();
        textCodFaixa3.setBounds(150,455,100,25);
        
        JButton add2 = new JButton("Adicionar");
		add2.setBounds(280,455,150,30);
        
        add2.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
        		String cod_album = textCodAlbumP.getText();
        		String num_faixa = textCodFaixa3.getText();
        		try {
					statement.executeUpdate("INSERT INTO faixa_play VALUES ((SELECT p.cod_p FROM playlist p WHERE p.cod_p >= ALL(SELECT cod_p FROM playlist)), '" + cod_album + "','" + num_faixa + "', 0, '01.01.0001')");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        	}
        	
        });

        JPanel painelAddMusicaNovaPlaylist = new JPanel();
        painelAddMusicaNovaPlaylist.setBounds(0,0,565,620);
        painelAddMusicaNovaPlaylist.setLayout(null);
        painelAddMusicaNovaPlaylist.add(voltar4);
        painelAddMusicaNovaPlaylist.add(labelListaMusicasP);
        painelAddMusicaNovaPlaylist.add(labelCodFaixa3);
        painelAddMusicaNovaPlaylist.add(textCodFaixa3);
        painelAddMusicaNovaPlaylist.add(add2);
        
        frame.setSize(565,620);
		frame.setResizable(false);
		frame.setLayout(cardLayout);
		frame.add(painelPrincipal, "painelPrincipal");
		frame.add(painelDeAlteracaoDePlaylist, "painelDeAlteracaoDePlaylist");
		frame.add(painelAdicionarPlaylist, "painelAdicionarPlaylist");
		frame.add(painelAddMusicas, "painelAddMusicas");
		frame.add(painelAddMusicaNovaPlaylist, "painelAddMusicaNovaPlaylist");
		cardLayout.show(frame.getContentPane(), "painelPrincipal");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        } catch (Exception e) {
        	
            e.printStackTrace();
            
        }
		
	}
	
}
