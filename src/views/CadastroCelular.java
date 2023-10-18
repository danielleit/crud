package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import dao.CelularDAO;
import model.Celular;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CadastroCelular {
    private static DefaultTableModel celularTableModel;
    private static JTable celularTable;
    private static JTextField modeloField;
    private static JTextField fabricanteField;
    private static JTextField anoField;

    public static void criarGUI() {
        CelularDAO celularDao = new CelularDAO();

        // Criação do JFrame
        JFrame frame = new JFrame("Cadastro de Celular");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        // Criação do JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona margens

        // Labels e campos de texto
        JLabel modeloLabel = new JLabel("Modelo:");
        modeloField = new JTextField(20);

        JLabel fabricanteLabel = new JLabel("Fabricante:");
        fabricanteField = new JTextField(20);

        JLabel anoLabel = new JLabel("Ano de Lançamento:");
        anoField = new JTextField(20);

        // Botão de cadastro
        JButton cadastrarButton = new JButton("Cadastrar");

        // Tabela de celulares
        celularTableModel = new DefaultTableModel();
        celularTableModel.addColumn("ID");
        celularTableModel.addColumn("Modelo");
        celularTableModel.addColumn("Fabricante");
        celularTableModel.addColumn("Ano");

        celularTable = new JTable(celularTableModel);
        JScrollPane scrollPane = new JScrollPane(celularTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botões de atualização e exclusão
        JButton atualizarButton = new JButton("Atualizar");
        JButton excluirButton = new JButton("Excluir");

        // Definindo o mesmo tamanho para os botões "Atualizar" e "Excluir"
        Dimension buttonSize = new Dimension(100, 30);
        atualizarButton.setPreferredSize(buttonSize);
        excluirButton.setPreferredSize(buttonSize);

        // Centraliza os botões horizontalmente
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(atualizarButton);
        buttonPanel.add(excluirButton);

        // Imprime os objetos da lista
        ArrayList<Celular> celulares = celularDao.read();
        for (Celular celular : celulares) {
            String[] rowData = { celular.getId().toString(), celular.getModelo(), celular.getFabricante(),
                    celular.getAno().toString() };
            celularTableModel.addRow(rowData);
        }

        // Ação do botão de cadastro
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelo = modeloField.getText();
                String fabricante = fabricanteField.getText();
                String ano = anoField.getText();

                Celular celular = new Celular(modelo, fabricante, Integer.parseInt(ano));
                celularDao.create(celular);

                // Adicione os dados do celular à tabela
                ArrayList<Celular> celulares = celularDao.read();
                Celular novoCelular = celulares.get(celulares.size() - 1);
                String[] rowData = { novoCelular.getId().toString(), novoCelular.getModelo(),
                        novoCelular.getFabricante(), novoCelular.getAno().toString() };
                celularTableModel.addRow(rowData);

                // Limpa os campos de entrada
                modeloField.setText("");
                fabricanteField.setText("");
                anoField.setText("");
            }
        });

        // Ação do botão de atualização
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = celularTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String id = celularTableModel.getValueAt(selectedRow, 0).toString();
                    String modelo = modeloField.getText();
                    String fabricante = fabricanteField.getText();
                    String ano = anoField.getText();

                    // Atualize o objeto na lista
                    celularDao.update(new Celular(Integer.parseInt(id), modelo, fabricante, Integer.parseInt(ano)));

                    celularTableModel.setValueAt(modelo, selectedRow, 1);
                    celularTableModel.setValueAt(fabricante, selectedRow, 2);
                    celularTableModel.setValueAt(ano, selectedRow, 3);
                }
            }
        });

        // Ação do botão de exclusão
        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = celularTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String id = celularTableModel.getValueAt(selectedRow, 0).toString();

                    celularTableModel.removeRow(selectedRow);

                    // Exclua o objeto da lista
                    celularDao.delete(Integer.parseInt(id));
                }
            }
        });

        // Adiciona ListSelectionListener para capturar seleções na tabela
        celularTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = celularTable.getSelectedRow();
                if (selectedRow >= 0) {
                    modeloField.setText(celularTableModel.getValueAt(selectedRow, 1).toString());
                    fabricanteField.setText(celularTableModel.getValueAt(selectedRow, 2).toString());
                    anoField.setText(celularTableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        // Adiciona os componentes ao painel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(modeloLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(modeloField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(fabricanteLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(fabricanteField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(anoLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(anoField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(cadastrarButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel ao JFrame
        frame.add(panel);

        // Torna o JFrame visível
        frame.setVisible(true);
    }
}