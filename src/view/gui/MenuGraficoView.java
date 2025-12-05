package view.gui;

import model.Gerenciador;
import model.Lista;
import model.GerenciadorIO;
import view.IMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Interface gráfica principal da aplicação (Menu Principal).
 * <p>
 * Esta classe estende {@link JFrame} e apresenta o ponto de entrada visual do sistema.
 * Exibe a lista de todas as listas de tarefas criadas e fornece botões para as operações
 * de gestão global: criar nova lista, abrir, renomear, excluir, salvar e carregar ficheiros.
 * </p>
 */
public class MenuGraficoView extends JFrame implements IMenuView {
    private Gerenciador gerenciador;
    private JList<String> listaListas;
    private DefaultListModel<String> listaModel;
    private JButton btnNovaLista, btnExcluir, btnRenomear, btnAbrir, btnSalvarESair, btnCarregarArquivo;
    private JLabel lblTitulo, lblInfo;
    private JPanel painelTopo;
    private int listaSelecionada = -1;

    /**
     * Construtor do Menu Principal Gráfico.
     * <p>
     * Inicializa a janela, carrega o estado do {@link Gerenciador}, configura o layout,
     * cria os componentes visuais (lista, botões, títulos) e define os ouvintes de eventos (listeners)
     * para interagir com o utilizador.
     * </p>
     */
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

    /**
     * Torna a janela do menu visível ao utilizador.
     */
    @Override
    public void mostrar() {
        setVisible(true);
    }

    /**
     * Verifica se existe uma lista válida selecionada na interface.
     *
     * @return {@code true} se o índice selecionado for válido dentro do modelo; {@code false} caso contrário.
     */
    private boolean getListaSelecionadaOK() {
        return (listaListas.getSelectedIndex() >= 0 && listaListas.getSelectedIndex() < listaModel.size());
    }

    /**
     * Atualiza o componente visual (JList) com os dados atuais do {@link Gerenciador}.
     * <p>
     * Limpa o modelo visual e preenche-o novamente com os nomes formatados das listas.
     * Também reseta o estado dos botões que dependem de seleção (Abrir, Renomear, Excluir).
     * </p>
     */
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

    /**
     * Formata o nome da lista para exibição, incluindo o seu tipo de forma legível.
     * <p>
     * Converte o nome da classe interna (ex: "ListaMeta") para uma string amigável (ex: "Lista de Tarefas").
     * </p>
     *
     * @param lista A lista a ser formatada.
     * @return Uma string contendo o tipo amigável e o nome da lista (ex: "Lista de Tarefas: Trabalho").
     */
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

    /**
     * Inicia o fluxo de criação de uma nova lista.
     * <p>
     * Exibe diálogos para o utilizador escolher o tipo de lista e inserir o nome.
     * Cria a lista no gerenciador e salva o estado automaticamente.
     * </p>
     */
    private void criarNovaLista() {
        String[] tipos = {"Padrão", "Tarefas", "Compras", "Mídia"};
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
                    case "Tarefas":
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

    /**
     * Abre a visualização detalhada da lista selecionada.
     * <p>
     * Instancia uma {@link ListaGraficaView} para a lista escolhida e fecha o menu principal.
     * </p>
     *
     * @param indice O índice da lista selecionada.
     */
    private void abrirLista(int indice) {
        java.util.List<Lista> listas = gerenciador.getConjListas();
        if (indice >= 0 && indice < listas.size()) {
            Lista selecionada = listas.get(indice);
            ListaGraficaView view = new ListaGraficaView(gerenciador, selecionada);
            view.mostrar();
            this.dispose();
        }
    }

    /**
     * Exclui a lista selecionada após confirmação do utilizador.
     *
     * @param indice O índice da lista a ser excluída.
     */
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

    /**
     * Permite renomear a lista selecionada.
     * <p>
     * Solicita o novo nome através de um diálogo e atualiza o gerenciador.
     * </p>
     *
     * @param indice O índice da lista a ser renomeada.
     */
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

    /**
     * Salva o estado atual do sistema e encerra a aplicação.
     *
     * @throws IOException Se ocorrer um erro durante o processo de gravação do ficheiro.
     */
    private void salvarESair() throws IOException {
        gerenciador.salvarEstado();
        System.exit(0);
    }
}