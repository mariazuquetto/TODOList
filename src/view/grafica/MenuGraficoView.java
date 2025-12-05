package view.grafica;

import model.Gerenciador;
import model.Lista;
import model.GerenciadorIO;
import view.IMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MenuGraficoView extends JFrame implements IMenuView {
    private Gerenciador gerenciador;
    private JList<String> listaListas;
    private DefaultListModel<String> listaModel;
    private JButton btnNovaLista, btnExcluir, btnRenomear, btnAbrir, btnSalvarESair, btnCarregarArquivo;
    private JLabel lblTitulo, lblInfo;
    private JPanel painelTopo;
    private int listaSelecionada = -1;

    public MenuGraficoView() {
        super("TODOList");
        this.gerenciador = Gerenciador.carregarEstado();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de título e subtitulo centralizados
        lblTitulo = new JLabel("TODOList - Menu", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblInfo = new JLabel(" ");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.add(lblTitulo);
        painelTopo.add(Box.createVerticalStrut(8));
        painelTopo.add(lblInfo);

        // Lista e modelo
        listaModel = new DefaultListModel<>();
        listaListas = new JList<>(listaModel);
        listaListas.setFont(new Font("Arial", Font.PLAIN, 18));
        listaListas.setFixedCellHeight(34);
        JScrollPane scroll = new JScrollPane(listaListas);
        scroll.setPreferredSize(new Dimension(400, 350));

        // Botões
        btnNovaLista = new JButton("Nova Lista");
        btnAbrir = new JButton("Abrir");
        btnRenomear = new JButton("Renomear");
        btnExcluir = new JButton("Excluir");
        btnSalvarESair = new JButton("Salvar e Sair");
        btnCarregarArquivo = new JButton("Carregar Arquivo"); // novo botão

        btnAbrir.setEnabled(false);
        btnRenomear.setEnabled(false);
        btnExcluir.setEnabled(false);

        JPanel painelOpcoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelOpcoes.add(btnNovaLista);
        painelOpcoes.add(btnAbrir);
        painelOpcoes.add(btnRenomear);
        painelOpcoes.add(btnExcluir);
        painelOpcoes.add(btnCarregarArquivo); // adiciona botão novo
        painelOpcoes.add(btnSalvarESair);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelOpcoes, BorderLayout.SOUTH);

        // Listeners
        listaListas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                listaSelecionada = listaListas.getSelectedIndex();
                boolean temLista = listaSelecionada >= 0;
                btnAbrir.setEnabled(temLista);
                btnExcluir.setEnabled(temLista);
                btnRenomear.setEnabled(temLista);
            }
        });

        listaListas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int idx = listaListas.locationToIndex(evt.getPoint());
                if (idx >= 0 && listaListas.getCellBounds(idx, idx).contains(evt.getPoint())) {
                    listaListas.setSelectedIndex(idx);
                } else {
                    listaListas.clearSelection();
                }
                if (evt.getClickCount() == 2 && idx >= 0) {
                    abrirLista(idx);
                }
            }
        });

        btnNovaLista.addActionListener(e -> criarNovaLista());
        btnAbrir.addActionListener(e -> {
            if (getListaSelecionadaOK()) {
                abrirLista(listaSelecionada);
            }
        });
        btnExcluir.addActionListener(e -> {
            if (getListaSelecionadaOK()) {
                excluirLista(listaSelecionada);
            }
        });
        btnRenomear.addActionListener(e -> {
            if (getListaSelecionadaOK()) {
                renomearLista(listaSelecionada);
            }
        });
        btnSalvarESair.addActionListener(e -> {
            try {
                salvarESair();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Novo listener para carregar arquivo
        btnCarregarArquivo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // filtro para mostrar apenas arquivos .ser
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de dados (*.ser)", "ser"));

            int resultado = fileChooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                String caminho = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    Gerenciador novo = GerenciadorIO.carregarArquivo(caminho);
                    gerenciador = novo;
                    atualizarListas();
                    JOptionPane.showMessageDialog(this,
                            "Arquivo carregado com sucesso!",
                            "Carregar Arquivo",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao carregar arquivo: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        atualizarListas();
        setVisible(true);
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    private boolean getListaSelecionadaOK() {
        return (listaListas.getSelectedIndex() >= 0 && listaListas.getSelectedIndex() < listaModel.size());
    }

    private void atualizarListas() {
        listaModel.clear();
        java.util.List<Lista> listas = gerenciador.getConjListas();
        if (listas == null || listas.isEmpty()) {
            lblInfo.setText("Nenhuma lista criada.");
        } else {
            lblInfo.setText("Selecione uma lista:");
            for (Lista l : listas) {
                listaModel.addElement(formatarNomeLista(l));
            }
        }
        listaSelecionada = -1;
        listaListas.clearSelection();
        btnAbrir.setEnabled(false);
        btnRenomear.setEnabled(false);
        btnExcluir.setEnabled(false);
    }

    // Método para formatar nome + tipo da lista no padrão amigável
    private String formatarNomeLista(Lista lista) {
        String tipo;
        switch (lista.getClass().getSimpleName()) {
            case "ListaPadrao":
                tipo = "Lista Padrão";
                break;
            case "ListaMeta":
                tipo = "Lista de Tarefas";
                break;
            case "ListaCompra":
                tipo = "Lista de Compras";
                break;
            case "ListaMidia":
                tipo = "Lista de Mídias";
                break;
            default:
                tipo = lista.getClass().getSimpleName();
        }
        return tipo + ": " + lista.getNome();
    }

    private void criarNovaLista() {
        String[] tipos = {"Padrão", "Meta", "Compras", "Mídia"};
        String tipoSelecionado = (String) JOptionPane.showInputDialog(
                this,
                "Escolha o tipo de lista:",
                "Nova Lista",
                JOptionPane.PLAIN_MESSAGE,
                null,
                tipos,
                tipos[0]
        );
        if (tipoSelecionado != null) {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome da lista:");
            if (nome != null && !nome.trim().isEmpty()) {
                String tipoInterno = null;
                switch (tipoSelecionado) {
                    case "Padrão":
                        tipoInterno = "ListaPadrao";
                        break;
                    case "Meta":
                        tipoInterno = "ListaMeta";
                        break;
                    case "Compras":
                        tipoInterno = "ListaCompra";
                        break;
                    case "Mídia":
                        tipoInterno = "ListaMidia";
                        break;
                }
                if (tipoInterno != null) {
                    gerenciador.criarLista(tipoInterno, nome);
                    try {
                        gerenciador.salvarEstado();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar lista automaticamente!\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    atualizarListas();
                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de lista inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else if (nome != null) {
                JOptionPane.showMessageDialog(this, "Nome da lista não pode ser vazio!", "Nome inválido", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirLista(int indice) {
        java.util.List<Lista> listas = gerenciador.getConjListas();
        if (indice >= 0 && indice < listas.size()) {
            Lista selecionada = listas.get(indice);
            ListaGraficaView view = new ListaGraficaView(gerenciador, selecionada);
            view.mostrar();
            this.dispose();
        }
    }

    private void excluirLista(int indice) {
        java.util.List<Lista> listas = gerenciador.getConjListas();
        if (indice >= 0 && indice < listas.size()) {
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja excluir a lista?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );
            if (resposta == JOptionPane.YES_OPTION) {
                gerenciador.excluirLista(indice + 1);
                try {
                    gerenciador.salvarEstado();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar lista automaticamente!\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
                atualizarListas();
            }
        }
    }

    private void renomearLista(int indice) {
        java.util.List<Lista> listas = gerenciador.getConjListas();
        if (indice >= 0 && indice < listas.size()) {
            String novoNome = JOptionPane.showInputDialog(this, "Novo nome para a lista:");
            if (novoNome != null && !novoNome.trim().isEmpty()) {
                gerenciador.getConjListas().get(indice).setNome(novoNome);
                try {
                    gerenciador.salvarEstado();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar lista automaticamente!\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
                atualizarListas();
            } else if (novoNome != null) {
                JOptionPane.showMessageDialog(this, "Nome da lista não pode ser vazio!", "Nome inválido", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void salvarESair() throws IOException {
        gerenciador.salvarEstado();
        System.exit(0);
    }
}