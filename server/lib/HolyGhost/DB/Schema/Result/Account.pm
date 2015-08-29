use utf8;
package HolyGhost::DB::Schema::Result::Account;

# Created by DBIx::Class::Schema::Loader
# DO NOT MODIFY THE FIRST PART OF THIS FILE

use strict;
use warnings;

use base 'DBIx::Class::Core';
__PACKAGE__->table("account");
__PACKAGE__->add_columns(
  "id",
  {
    data_type         => "integer",
    is_auto_increment => 1,
    is_nullable       => 0,
    sequence          => "account_id_seq",
  },
  "username",
  { data_type => "varchar", is_nullable => 0, size => 63 },
  "password",
  { data_type => "char", is_nullable => 0, size => 60 },
  "realname",
  { data_type => "varchar", is_nullable => 1, size => 128 },
);
__PACKAGE__->set_primary_key("id");
__PACKAGE__->add_unique_constraint("account_username_key", ["username"]);
__PACKAGE__->has_many(
  "sightings",
  "HolyGhost::DB::Schema::Result::Sighting",
  { "foreign.account_id" => "self.id" },
  { cascade_copy => 0, cascade_delete => 0 },
);


# Created by DBIx::Class::Schema::Loader v0.07043 @ 2015-08-29 01:31:33
# DO NOT MODIFY THIS OR ANYTHING ABOVE! md5sum:z/bF7TYRy8qiPeqxnOWq9Q
__PACKAGE__->load_components(qw/EncodedColumn/);
__PACKAGE__->add_columns(
   '+password' => {
      encode_column       => 1,
      encode_class        => 'Crypt::Eksblowfish::Bcrypt',
      encode_args         => {key_nul => 1},
      encode_check_method => 'check_password'
   }
);

1;
