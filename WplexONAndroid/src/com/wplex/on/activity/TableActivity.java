package com.wplex.on.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.gigio.utils.ScreenUtils;
import com.wplex.on.R;
import com.wplex.on.model.BlockTableRowData;
import com.wplex.on.model.BlocksModel;
import com.wplex.on.view.ColumnHeaderView;

/**
 * TODO LIST
 * - ajeitar ícone
 * - filtros da tabela: bloco, linha, tipo, sentido (cumulativos!)
 * - opção para esconder/visualizar coluna
 * - gestir o ciclo de vida: ex, preservar a ordenação dos dados ao mudar orientamento da tela
 * - menu contextual para cada linha da tabela (long press), com várias opções: marcar/desmarcar viagem,
 *   eliminar viagem, modificar tempos
 * - dialog para mudar os tempos de viagem, com validação
 * - menu da activity, com opção para criar viagem blocada, criar bloco, excluir bloco
 * - dialogs para as três ações
 * - salvar arquivo (opção do menu)
 * - abrir arquivo (opção do menu)
 * - criar arquivo (opção do menu)
 * 
 * @author Francesco Bertolino
 */
public class TableActivity extends Activity
{
	private BlocksModel blocksModel;

	private int orderByColumn = BlockTableRowData.BLOCK_ID_COLUMN;

	private boolean ascending = true;

	private final List<ColumnHeaderView> headers = new ArrayList<ColumnHeaderView>(
			6);

	private static final int DIALOG_FILTER = 0;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		this.blocksModel = (BlocksModel) getIntent().getExtras()
				.getSerializable("blocksModel");

		loadHeaders();
		loadTable();
	}

	private void loadHeaders()
	{
		final int screenWidth = ScreenUtils.getScreenWidth(this);
		final int w = screenWidth / 6;
		final int h = (int) ScreenUtils.convertFromDPIToPixels(this, 20);
		final LayoutParams params = new LayoutParams(w, h);

		this.headers.add(new ColumnHeaderView(this, params,
				BlockTableRowData.BLOCK_ID_COLUMN, R.string.block,
				R.string.block_up, R.string.block_down));
		this.headers.add(new ColumnHeaderView(this, params,
				BlockTableRowData.LINE_COLUMN, R.string.line, R.string.line_up,
				R.string.line_down));
		this.headers.add(new ColumnHeaderView(this, params,
				BlockTableRowData.KIND_COLUMN, R.string.kind, R.string.kind_up,
				R.string.kind_down));
		this.headers.add(new ColumnHeaderView(this, params,
				BlockTableRowData.DIRECTION_COLUMN, R.string.direction,
				R.string.direction_up, R.string.direction_down));
		this.headers.add(new ColumnHeaderView(this, params,
				BlockTableRowData.START_TIME_COLUMN, R.string.start,
				R.string.start_up, R.string.start_down));
		this.headers.add(new ColumnHeaderView(this, params,
				BlockTableRowData.END_TIME_COLUMN, R.string.end,
				R.string.end_up, R.string.end_down));

		final TableRow row = (TableRow) findViewById(R.id.headerRow);
		for (final ColumnHeaderView header : this.headers)
			row.addView(header);
	}

	public void resetHeadersText()
	{
		for (final ColumnHeaderView header : this.headers)
			if (this.orderByColumn != header.getColumn())
				header.setText(getString(header.getDefaultId()));
	}

	public void loadTable()
	{
		final TableLayout table = (TableLayout) findViewById(R.id.tableLayoutContent);
		table.removeAllViews();

		final int screenWidth = ScreenUtils.getScreenWidth(this);
		final int w = screenWidth / 6;
		final int h = (int) ScreenUtils.convertFromDPIToPixels(this, 20);
		final LayoutParams params = new LayoutParams(w, h);

		TextView txtBlock;
		TextView line;
		TextView kind;
		TextView direction;
		TextView startTime;
		TextView endTime;
		TableRow row;
		boolean color = true;
		final List<BlockTableRowData> blockTableRowData = this.blocksModel
				.getBlockTableRowData(this.orderByColumn, this.ascending);
		for (final BlockTableRowData data : blockTableRowData)
		{
			txtBlock = new TextView(this);
			txtBlock.setLayoutParams(params);
			line = new TextView(this);
			line.setLayoutParams(params);
			kind = new TextView(this);
			kind.setLayoutParams(params);
			direction = new TextView(this);
			direction.setLayoutParams(params);
			startTime = new TextView(this);
			startTime.setLayoutParams(params);
			endTime = new TextView(this);
			endTime.setLayoutParams(params);

			txtBlock.setText(String.valueOf(data.getBlockId()));
			line.setText(data.getLine());
			kind.setText(data.getItineraryKind());
			direction.setText(data.getDirection());
			startTime.setText(data.getStartTime());
			endTime.setText(data.getEndTime());

			row = new TableRow(this);
			row.setBackgroundColor(color ? Color.BLACK : Color.DKGRAY);
			color = !color;
			row.addView(txtBlock);
			row.addView(line);
			row.addView(kind);
			row.addView(direction);
			row.addView(startTime);
			row.addView(endTime);
			table.addView(row);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v instanceof ColumnHeaderView)
		{
			final int id = ((ColumnHeaderView) v).getDefaultId();
			menu.setHeaderTitle(getString(id));
			menu.add(0, id, 0, getString(R.string.filter));
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if (item.getTitle().equals(getString(R.string.filter)))
			showDialog(DIALOG_FILTER);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		Dialog dialog = null;
		switch (id)
		{
			case DIALOG_FILTER:
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						this);
				// TODO implementar filtros
				builder.setTitle("Filtro Bloco");
				final String[] items = new String[] { "Todos", "1", "2", "3",
						"4", "5", "6", "7", "8" };
				builder.setItems(items, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int item)
					{
						Toast.makeText(getApplicationContext(), items[item],
								Toast.LENGTH_SHORT).show();
					}
				});
				dialog = builder.create();
				break;
			default:
				dialog = null;
		}
		return dialog;
	}

	public void setOrderByColumn(int orderByColumn)
	{
		this.orderByColumn = orderByColumn;
	}

	public boolean isAscending()
	{
		return this.ascending;
	}

	public void setAscending(boolean ascending)
	{
		this.ascending = ascending;
	}

}
