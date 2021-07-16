package com.fetchrewards.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fetchrewards.bean.AccountBalance;
import com.fetchrewards.bean.BalanceDeducted;
import com.fetchrewards.bean.TransactionDetails;

@Service
public class ServiceImpl {

	Map<String, List<Integer>> map = new HashMap<>();
	Map<Integer, TransactionDetails> mapid = new LinkedHashMap<>();

	static int balanceAvailable;
	static int id = 0;

	public ResponseEntity<String> addBalance(TransactionDetails transaction) {

		if (transaction.getPoints() <= 0)
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);

		List<Integer> idList = new ArrayList<>();
		idList.add(++id);
		map.put(transaction.getName(), idList);
		mapid.put(id, transaction);
		balanceAvailable += transaction.getPoints();

		return new ResponseEntity<String>("Sucess", HttpStatus.OK);

	}

	public ResponseEntity<List<BalanceDeducted>> spendRewardPoints(int points) {

		List<BalanceDeducted> balanceDeductedList = new ArrayList<BalanceDeducted>();
		if (points > balanceAvailable)
			return new ResponseEntity<List<BalanceDeducted>>(balanceDeductedList, HttpStatus.BAD_REQUEST);

		List<Integer> ids = new ArrayList<>();
		for (int i : mapid.keySet()) {

			if (mapid.get(i).getPoints() >= points) {

				balanceDeductedList
						.add(new BalanceDeducted(mapid.get(i).getName(), -1 * points, LocalDateTime.now()));

				System.out.println(mapid.get(i).getName() + ", -" + points + ", " + LocalDateTime.now());

				mapid.get(i).setPoints(mapid.get(i).getPoints() - points);
				if (mapid.get(i).getPoints() == 0) {
					map.get(mapid.get(i).getName()).remove(new Integer(i));
					ids.add(i);
				}
				break;
			} else {
				if (mapid.get(i).getPoints() > 0) {
					balanceDeductedList.add(new BalanceDeducted(mapid.get(i).getName(),
							-1 * mapid.get(i).getPoints(), LocalDateTime.now()));
					System.out.println(mapid.get(i).getName() + ", -" + mapid.get(i).getPoints()
							+ ", " + LocalDateTime.now());
					points -= mapid.get(i).getPoints();
					mapid.get(i).setPoints(0);
					map.get(mapid.get(i).getName()).remove(new Integer(i));
					ids.add(i);
				}
			}

		}

		for (int i : ids) {
			mapid.remove(i);
		}
		balanceAvailable -= points;

		return new ResponseEntity<List<BalanceDeducted>>(balanceDeductedList, HttpStatus.OK);
	}

	public ResponseEntity<List<AccountBalance>> payersBalance() {

		List<AccountBalance> balance = new ArrayList<AccountBalance>();
		for (String payee : map.keySet()) {
			int amount = 0;
			List<Integer> ids = map.get(payee);

			for (int id : ids) {
				amount += mapid.get(id).getPoints();
			}

			balance.add(new AccountBalance(payee, amount));

			System.out.println(payee + " : " + amount);
		}

		return new ResponseEntity<List<AccountBalance>>(balance, HttpStatus.OK);
	}

}
