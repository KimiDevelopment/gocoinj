/*
 * Copyright 2013 Google Inc.
 * Copyright 2015 Andreas Schildbach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gocoinj.params;

import org.gocoinj.core.*;
import org.gocoinj.net.discovery.*;

import java.net.*;

import static com.google.common.base.Preconditions.*;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends AbstractBitcoinNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 1000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750;

    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1e0fffffL);
        dumpedPrivateKeyHeader = 166;
        addressHeader = 38;
        p2shHeader = 5;
        segwitAddressHrp = "go";
        port = 6888;
        packetMagic = 0xe4e2f4e9L;
        bip32HeaderP2PKHpub = 0x0488b21e; // The 4 byte header that serializes in base58 to "xpub".
        bip32HeaderP2PKHpriv = 0x0488ade4; // The 4 byte header that serializes in base58 to "xprv"
        bip32HeaderP2WPKHpub = 0x04b24746; // The 4 byte header that serializes in base58 to "zpub".
        bip32HeaderP2WPKHpriv = 0x04b2430c; // The 4 byte header that serializes in base58 to "zprv"

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setTime(1566299000L);
        genesisBlock.setNonce(1173059);
        genesisBlock.setMerkleRoot(new Sha256Hash("c77c6bb71742c7d4cbe0abf50f546fd01105238f8f2f01ce631dcf77108c2474"));
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 2102400;
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("cedaf8dd38a8936e33c755f9010d2d454e197d8cb6ad55834f68c2a44857a058"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        // checkpoints.put(10, Sha256Hash.wrap("2446d3fbe939985dfb087e6aa96a97fb9fc4ea0e9c4f1f484417595e2407a33c"));

        dnsSeeds = new String[] {
                "seed1.gocoinproject.org",
                "seed2.gocoinproject.org",
        };

        addrSeeds = new int[] {
                0x224047a7,
        };
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
