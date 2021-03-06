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

import com.gigio.utils.ScreenUtils;
import com.wplex.on.R;
import com.wplex.on.model.BlockTableFiltersMap;
import com.wplex.on.model.BlockTableRowData;
import com.wplex.on.model.BlocksModel;
import com.wplex.on.model.EBlockTableColumns;
import com.wplex.on.view.ColumnHeaderView;

/**
 * TODO LIST
 * - ajeitar �cone
 * - op��o para esconder/visualizar coluna
 * - menu contextual para cada linha da tabela (long press), com v�rias op��es: marcar/desmarcar viagem,
 *   eliminar viagem, modificar tempos
 * - dialog para mudar os tempos de viagem, com valida��o
 * - menu da activity, com op��o para criar viagem blocada, criar bloco, excluir bloco
 * - dialogs para as tr�s a��es
 * - salvar arquivo (op��o do menu)
 * - abrir arquivo (op��o do menu)
 * - criar arquivo (op��o do menu)
 * 
 * @author Francesco Bertolino
 */
public class TableActivity extends Activity
{
	private BlocksModel blocksModel;

	private EBlockTableColumns orderByColumn = EBlockTableColumns.BLOCK;

	private boolean ascending = true;

	private EBlockTableColumns filterByColumn;

	private final List<ColumnHeaderView> headers = new ArrayList<ColumnHeaderView>(
			6);

	private static final int DIALOG_FILTER = 0;

	private static final int DIALOG_CONFIRM_EXIT = 1;

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

		if (savedInstanceState != null)
		{
			this.orderByColumn = (EBlockTableColumns) savedInstanceState
					.getSerializable("orderByColumn");
			this.ascending = savedInstanceState.getBoolean("ascending");
		}

		loadHeaders();
		loadTable();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable("orderByColumn", this.orderByColumn);
		outState.putBoolean("ascending", this.ascending);
	}

	private void loadHeaders()
	{
		final int screenWidth = ScreenUtils.getScreenWidth(this);
		final int w = screenWidth / 6;
		final int h = (int) ScreenUtils.convertFromDPIToPixels(this, 20);
		final LayoutParams params = new LayoutParams(w, h);

		this.headers.add(new ColumnHeaderView(this, params,
				EBlockTableColumns.BLOCK));
		this.headers.add(new ColumnHeaderView(this, params,
				EBlockTableColumns.LINE));
		this.headers.add(new ColumnHeaderView(this, params,
				EBlockTableColumns.KIND));
		this.headers.add(new ColumnHeaderView(this, params,
				EBlockTableColumns.DIRECTION));
		this.headers.add(new ColumnHeaderView(this, params,
				EBlockTableColumns.START_TIME));
		this.headers.add(new ColumnHeaderView(this, params,
				EBlockTableColumns.END_TIME));

		final TableRow row = (TableRow) findViewById(R.id.headerRow);
		for (final ColumnHeaderView header : this.headers)
			row.addView(header);
	}

	public void resetHeadersText()
	{
		for (final ColumnHeaderView header : this.headers)
			if (this.orderByColumn != header.getColumn())
				header.setText(getString(header.getColumn().getDefaultId()));
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
			final EBlockTableColumns column = ((ColumnHeaderView) v)
					.getColumn();
			final int id = column.getDefaultId();
			menu.setHeaderTitle(getString(id));
			if (!column.equals(EBlockTableColumns.START_TIME)
					&& !column.equals(EBlockTableColumns.END_TIME))
			{
				menu.add(0, id, 0, getString(R.string.filter));
				this.filterByColumn = column;
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if (item.getTitle().equals(getString(R.string.filter)))
		{
			if (this.filterByColumn != null)
				showDialog(DIALOG_FILTER);
		}
		return true;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog)
	{
		super.onPrepareDialog(id, dialog);
		switch (id)
		{
			case DIALOG_FILTER:
				removeDialog(id); // para evitar que o sistema pegue sempre o dialog anterior j� pronto...
		}
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
				builder.setTitle(getString(this.filterByColumn.getDefaultId()));
				final String[] filterItems = this.blocksModel.getFilterItems(
						getString(R.string.all), this.filterByColumn);
				builder.setItems(filterItems,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int item)
							{
								String value = null;
								if (item > 0)
									value = filterItems[item];
								BlockTableFiltersMap.getInstance().setFilter(
										TableActivity.this.filterByColumn,
										value);
								TableActivity.this.loadTable();
							}
						});
				dialog = builder.create();
				break;
			case DIALOG_CONFIRM_EXIT:
				final AlertDialog.Builder builder2 = new AlertDialog.Builder(
						this);
				builder2.setMessage(getString(R.string.confirmation_exit))
						.setCancelable(false)
						.setPositiveButton(getString(R.string.yes),
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog,
											int id)
									{
										TableActivity.this.finish();
									}
								})
						.setNegativeButton(getString(R.string.no),
								new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog,
											int id)
									{
										dialog.cancel();
									}
								});
				dialog = builder2.create();
				break;
		}
		return dialog;
	}

	@Override
	public void onBackPressed()
	{
		showDialog(DIALOG_CONFIRM_EXIT);
	}

	public EBlockTableColumns getOrderByColumn()
	{
		return this.orderByColumn;
	}

	public void setOrderByColumn(EBlockTableColumns orderByColumn)
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
