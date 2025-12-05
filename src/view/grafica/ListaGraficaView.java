package view.grafica;

import model.*;
import view.IListaView;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ListaGraficaView<S extends Item, T extends Lista<S>> extends JFrame implements IListaView {
    private Gerenciador gerenciador;
    private T lista;
    private DefaultListModel<S> modelItens;
    private JList<S> listaItens;
    private JButton btnNovoItem, btnRemoverItem, btnEditarItem, btnVoltar;
    private JLabel lblNomeLista, lblTipoLista;
    private JLabel lblInfoExtra;

    @SuppressWarnings("unchecked")
    public ListaGraficaView(Gerenciador gerenciador, Lista<?> lista) {
        this.gerenciador = gerenciador;
        this.lista = (T) lista;

        setTitle("Visualizar Lista");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelItens = new DefaultListModel<>();
        listaItens = new JList<>(modelItens);
        listaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaItens.setFont(new Font("Arial", Font.PLAIN, 17));
        listaItens.setFixedCellHeight(30);

        // Renderiza cada tipo de item com informações específicas
        listaItens.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel panel = new JPanel(new BorderLayout(10, 0));
            panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            if (value instanceof Item) {
                Item item = (Item) value;
                JLabel lblInfo = new JLabel();

                if (item instanceof ItemPadrao) {
                    lblInfo.setText(String.format("%s   [%s]", item.getTitulo(), item.getEstado().name()));
                } else if (item instanceof ItemMeta) {
                    ItemMeta meta = (ItemMeta) item;
                    String dataFormatada = model.Utilidades.formatarData(meta.getDataMeta());
                    lblInfo.setText(String.format("%s até %s   [%s]", meta.getTitulo(), dataFormatada, meta.getEstado().name()));
                } else if (item instanceof ItemCompra) {
                    ItemCompra compra = (ItemCompra) item;
                    double total = compra.getPreco() * compra.getQuantidade();
                    lblInfo.setText(String.format("%d %s(s) por R$%.2f cada  |  Total: R$%.2f        [%s]",
                            compra.getQuantidade(),
                            compra.getTitulo(),
                            compra.getPreco(),
                            total,
                            compra.getEstado().name()));
                } else if (item instanceof ItemMidia) {
                    ItemMidia midia = (ItemMidia) item;
                    String status = (midia.getEstado() == Estado.PENDENTE) ? "Pendente" : "Visto/Lido";
                    lblInfo.setText(String.format("Mídia: %s   |   Formato: %s   |   Nota: %.1f/10   |   Status: %s",
                            midia.getTitulo(),
                            midia.getFormatoMidia(),
                            midia.getNota(),
                            status));
                }

                panel.add(lblInfo, BorderLayout.CENTER);

                if (isSelected) {
                    panel.setBackground(list.getSelectionBackground());
                    lblInfo.setForeground(list.getSelectionForeground());
                } else {
                    panel.setBackground(list.getBackground());
                    lblInfo.setForeground(list.getForeground());
                }
            }

            return panel;
        });

        JScrollPane scroll = new JScrollPane(listaItens);

        lblNomeLista = new JLabel(lista.getNome(), JLabel.CENTER);
        lblNomeLista.setFont(new Font("Arial", Font.BOLD, 18));
        lblTipoLista = new JLabel("Tipo: " + tipoAmigavel(lista), JLabel.CENTER);
        lblTipoLista.setFont(new Font("Arial", Font.PLAIN, 14));

        lblInfoExtra = new JLabel("", JLabel.CENTER);
        lblInfoExtra.setFont(new Font("Arial", Font.ITALIC, 13));

        lblInfoExtra.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel painelTopo = new JPanel(new GridLayout(2, 1));
        painelTopo.add(lblNomeLista);
        painelTopo.add(lblTipoLista);

        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNovoItem = new JButton("Novo Item");
        btnEditarItem = new JButton("Editar Item");
        btnRemoverItem = new JButton("Remover Item");
        btnVoltar = new JButton("Voltar");

        painelAcoes.add(btnNovoItem);
        painelAcoes.add(btnEditarItem);
        painelAcoes.add(btnRemoverItem);
        painelAcoes.add(btnVoltar);

        // Painel inferior que junta botões + info extra
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(painelAcoes, BorderLayout.NORTH);
        painelInferior.add(lblInfoExtra, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        btnEditarItem.setEnabled(false);
        btnRemoverItem.setEnabled(false);

        atualizarItens();

        listaItens.addListSelectionListener(e -> {
            boolean selecionado = listaItens.getSelectedIndex() >= 0;
            btnEditarItem.setEnabled(selecionado);
            btnRemoverItem.setEnabled(selecionado);
        });

        btnNovoItem.addActionListener(e -> novoItem());
        btnEditarItem.addActionListener(e -> editarItem(listaItens.getSelectedIndex()));
        btnRemoverItem.addActionListener(e -> removerItem(listaItens.getSelectedIndex()));
        btnVoltar.addActionListener(e -> voltarMenu());
    }


    @Override
    public void mostrar() {
        setVisible(true);
    }

    private void acaoDeLista() {
        if (lista instanceof ListaCompra) {
            ListaCompra lc = (ListaCompra) lista;
            lblInfoExtra.setText(String.format("Quantidade total: %d   |   Preço total: R$%.2f",
                    lc.quantidadeTotal(), lc.precoTotal()));
        } else if (lista instanceof ListaMidia) {
            ListaMidia lm = (ListaMidia) lista;
            lblInfoExtra.setText(String.format("Mídias concluídas: %d   |   Total adicionadas: %d",
                    lm.getQuantidadeAvaliadas(), lm.getQuantidadeTotal()));
        } else {
            lblInfoExtra.setText(""); // não mostra nada para outros tipos
        }
    }

    private void atualizarItens() {
        modelItens.clear();
        for (S i : lista.getLista()) {
            modelItens.addElement(i);
        }
        listaItens.clearSelection();
        btnEditarItem.setEnabled(false);
        btnRemoverItem.setEnabled(false);

        // Atualiza informações extras
        acaoDeLista();
    }

    // ----------- NOVOITEM() AJUSTADO PARA CADA TIPO ---------------
    private void novoItem() {
        String tipoLista = lista.getClass().getSimpleName();

        String titulo = JOptionPane.showInputDialog(this, "Insira o título do seu item:");
        if (titulo == null) return; // cancelado
        if (titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O título não pode ser vazio!", "Nome inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            S item = null;

            switch (tipoLista) {
                case "ListaPadrao":
                    item = (S) gerenciador.criarItemPadrao(titulo);
                    break;

                case "ListaMeta": {
                    String data = JOptionPane.showInputDialog(this, "Insira o prazo do seu item (formato dd/mm/aaaa):");
                    if (data == null) return;
                    LocalDate dataFormatada = model.Utilidades.formatarData(data.trim());
                    if (!model.Utilidades.dataValida(dataFormatada)) {
                        JOptionPane.showMessageDialog(this, "Data inválida! Insira uma data no futuro.", "Data inválida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    item = (S) gerenciador.criarItemMeta(titulo, dataFormatada);
                    break;
                }
                case "ListaCompra": {
                    int quantidade;
                    while (true) {
                        String quantidadeStr = JOptionPane.showInputDialog(this, "Insira a quantidade (apenas números inteiros > 0):");
                        if (quantidadeStr == null) return;
                        try {
                            quantidade = Integer.parseInt(quantidadeStr.trim());
                            if (!model.Utilidades.valorValido(quantidade)) {
                                JOptionPane.showMessageDialog(this, "Quantidade inválida. Digite um inteiro maior que 0.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Erro: Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    double preco;
                    while (true) {
                        String precoStr = JOptionPane.showInputDialog(this, "Insira o preço (apenas números, ex: 24.50):");
                        if (precoStr == null) return;
                        try {
                            preco = Double.parseDouble(precoStr.trim().replace(",", "."));
                            if (!model.Utilidades.valorValido(preco)) {
                                JOptionPane.showMessageDialog(this, "Preço inválido. Digite um valor maior ou igual a 0.", "Valor inválido", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Erro: Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    item = (S) gerenciador.criarItemCompra(titulo, quantidade, preco);
                    break;
                }
                case "ListaMidia": {
                    String formato = JOptionPane.showInputDialog(this, "Insira o formato de mídia:");
                    if (formato == null) return;
                    if (!model.Utilidades.stringValida(formato.trim())) {
                        JOptionPane.showMessageDialog(this, "Formato não pode ser vazio.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    item = (S) gerenciador.criarItemMidia(titulo, formato.trim());
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de lista desconhecido. Não é possível criar itens.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            lista.adicionarItem(item);
            gerenciador.salvarEstado();
            atualizarItens();
            JOptionPane.showMessageDialog(this, "Item adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    // -------------------------------------------------------------

    private void editarItem(int indice) {
        if (indice >= 0 && indice < modelItens.size()) {
            S item = modelItens.get(indice);
            String tipo = lista.getClass().getSimpleName();

            // Menu de opções
            String[] opcoesBase = {"Título", "Estado"};
            String[] opcoesExtras;

            switch (tipo) {
                case "ListaMeta":
                    opcoesExtras = new String[]{"Prazo"};
                    break;
                case "ListaCompra":
                    opcoesExtras = new String[]{"Quantidade", "Preço"};
                    break;
                case "ListaMidia":
                    opcoesExtras = new String[]{"Formato de mídia", "Nota"};
                    break;
                default:
                    opcoesExtras = new String[]{};
            }

            String[] opcoes = new String[opcoesBase.length + opcoesExtras.length];
            System.arraycopy(opcoesBase, 0, opcoes, 0, opcoesBase.length);
            System.arraycopy(opcoesExtras, 0, opcoes, opcoesBase.length, opcoesExtras.length);

            String escolha = (String) JOptionPane.showInputDialog(
                    this,
                    "O que deseja editar?",
                    "Editar Item",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            if (escolha == null) return; // cancelado

            try {
                switch (escolha) {
                    case "Título":
                        String novoTitulo = JOptionPane.showInputDialog(this, "Novo título:", item.getTitulo());
                        if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
                            item.setTitulo(novoTitulo.trim());
                        }
                        break;

                    case "Estado": {
                        String[] estados = {"PENDENTE", "FEITO"};
                        String novoEstado = (String) JOptionPane.showInputDialog(
                                this,
                                "Selecione o estado:",
                                "Editar Estado",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                estados,
                                item.getEstado().name()
                        );
                        if (novoEstado != null) {
                            try {
                                item.setEstado(Estado.valueOf(novoEstado)); // usa diretamente o enum
                            } catch (IllegalArgumentException ex) {
                                JOptionPane.showMessageDialog(this, "Estado inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    }


                    case "Prazo":
                        String data = JOptionPane.showInputDialog(this, "Novo prazo (dd/mm/aaaa):");
                        if (data != null) {
                            LocalDate novaData = model.Utilidades.formatarData(data.trim());
                            if (model.Utilidades.dataValida(novaData)) {
                                ((ItemMeta) item).setDataMeta(novaData);
                            } else {
                                JOptionPane.showMessageDialog(this, "Data inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case "Quantidade":
                        String qtdStr = JOptionPane.showInputDialog(this, "Nova quantidade:", ((ItemCompra) item).getQuantidade());
                        if (qtdStr != null) {
                            int qtd = Integer.parseInt(qtdStr.trim());
                            if (model.Utilidades.valorValido(qtd)) {
                                ((ItemCompra) item).setQuantidade(qtd);
                            } else {
                                JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case "Preço":
                        String precoStr = JOptionPane.showInputDialog(this, "Novo preço:", ((ItemCompra) item).getPreco());
                        if (precoStr != null) {
                            double preco = Double.parseDouble(precoStr.trim().replace(",", "."));
                            if (model.Utilidades.valorValido(preco)) {
                                ((ItemCompra) item).setPreco(preco);
                            } else {
                                JOptionPane.showMessageDialog(this, "Preço inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case "Formato de mídia":
                        String formato = JOptionPane.showInputDialog(this, "Novo formato:", ((ItemMidia) item).getFormatoMidia());
                        if (formato != null && model.Utilidades.stringValida(formato.trim())) {
                            ((ItemMidia) item).setFormatoMidia(formato.trim());
                        }
                        break;

                    case "Nota":
                        String notaStr = JOptionPane.showInputDialog(this, "Nova nota:", ((ItemMidia) item).getNota());
                        if (notaStr != null) {
                            double nota = Double.parseDouble(notaStr.trim());
                            ((ItemMidia) item).setNota(nota);
                        }
                        break;
                }

                gerenciador.salvarEstado();
                atualizarItens();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao editar item: Entrada inválida." , "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void removerItem(int indice) {
        if (indice >= 0 && indice < modelItens.size()) {
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Excluir item?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );
            if (resposta == JOptionPane.YES_OPTION) {
                lista.getLista().remove(indice);
                try {
                    gerenciador.salvarEstado();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar após remoção: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
                atualizarItens();
            }
        }
    }

    private void voltarMenu() {
        MenuGraficoView menu = new MenuGraficoView();
        menu.mostrar();
        this.dispose();
    }

    private String tipoAmigavel(Lista lista) {
        switch (lista.getClass().getSimpleName()) {
            case "ListaPadrao":
                return "Padrão";
            case "ListaMeta":
                return "Tarefas";
            case "ListaCompra":
                return "Compras";
            case "ListaMidia":
                return "Mídias";
            default:
                return lista.getClass().getSimpleName();
        }
    }
}